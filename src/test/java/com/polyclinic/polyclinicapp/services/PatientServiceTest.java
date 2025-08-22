package com.polyclinic.polyclinicapp.services;

import com.polyclinic.polyclinicapp.ModelMapping;
import com.polyclinic.polyclinicapp.dto.PatientsDTO;
import com.polyclinic.polyclinicapp.entity.Patients;
import com.polyclinic.polyclinicapp.repositories.PatientsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientsRepository patientsRepository;
    @Mock
    private ModelMapping modelMapping;

    @InjectMocks
    private PatientService patientService;

    @Test
    void testGetAllPatients() {
        List<Patients> patientsList = List.of(new Patients());
        when(patientsRepository.findAll()).thenReturn(patientsList);
        when(modelMapping.patientsDTOList(patientsList)).thenReturn(List.of(new PatientsDTO()));

        List<PatientsDTO> result = patientService.getAllPatients();

        Assertions.assertFalse(result.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,true",   // patient exists
            "2,false"   // patient does not exist
    })
    void testGetPatientsById(int id, boolean exists) {
        Patients patient = new Patients();
        PatientsDTO patientDTO = new PatientsDTO();

        if (exists) {
            when(patientsRepository.findById(id)).thenReturn(Optional.of(patient));
            when(modelMapping.patientsDTO(patient)).thenReturn(patientDTO);
        } else {
            when(patientsRepository.findById(id)).thenReturn(Optional.empty());
        }

        ResponseEntity<PatientsDTO> result = patientService.getPatientsById(id);

        if (exists) {
            Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
            Assertions.assertEquals(patientDTO, result.getBody());
        } else {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
            Assertions.assertNull(result.getBody());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "ValidName,true",   // doctor exists
            "InvalidName,false"   // doctor does not exist
    })
    void testGetPatientsByName(String name, boolean exists) {
        List<Patients> patients = List.of(new Patients());
        List<PatientsDTO> patientsList = List.of(new PatientsDTO());

        if (exists) {
            when(patientsRepository.findByPatientNameContaining(name)).thenReturn(patients);
            when(modelMapping.patientsDTOList(patients)).thenReturn(patientsList);
        } else {
            when(patientsRepository.findByPatientNameContaining(name)).thenReturn(List.of());
        }

        ResponseEntity<List<PatientsDTO>> result = patientService.getPatientsByName(name);

        if (exists) {

            Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
            Assertions.assertEquals(patientsList, result.getBody());
        } else {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
            Assertions.assertNull(result.getBody());
        }
    }

    @Test
    void testAddPatientInvalidName() {
        PatientsDTO dto = new PatientsDTO();
        dto.setPatientName("");
        dto.setAge(20);
        dto.setPhoneNumber("12345");

        String result = patientService.addPatient(dto);

        Assertions.assertEquals("Patient name cannot be empty", result);
    }

    @Test
    void testUpdatePatientInvalidId() {
        PatientsDTO dto = new PatientsDTO();
        dto.setPatientId(1);
        when(patientsRepository.existsById(dto.getPatientId())).thenReturn(false);

        String result = patientService.updatePatient(dto);

        Assertions.assertEquals("Patient not found", result);
    }

    @Test
    void testDeletePatientInvalidId() {
        int id = -1;

        String result = patientService.deletePatient(id);

        Assertions.assertEquals("Invalid patient ID", result);
    }
}