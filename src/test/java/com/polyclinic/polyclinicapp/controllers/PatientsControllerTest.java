package com.polyclinic.polyclinicapp.controllers;

import com.polyclinic.polyclinicapp.dto.PatientsDTO;
import com.polyclinic.polyclinicapp.services.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientsControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientsController patientsController;

    @Test
    void testGetAllPatients() {
        // Arrange
        List<PatientsDTO> expectedPatients = List.of(new PatientsDTO());
        when(patientService.getAllPatients()).thenReturn(expectedPatients);

        // Act
        List<PatientsDTO> result = patientsController.getAllPatients();

        // Assert
        assertNotNull(result);
        assertEquals(expectedPatients, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1,true",
            "2,false"
    })
    void testGetPatientsById(int id, boolean exists) {
        // Arrange
        PatientsDTO expectedPatient = new PatientsDTO();

        if (exists) {
            when(patientService.getPatientsById(id)).thenReturn(ResponseEntity.ok(expectedPatient));
        } else {
            when(patientService.getPatientsById(id)).thenReturn(ResponseEntity.notFound().build());
        }

        // Act
        ResponseEntity<PatientsDTO> result = patientsController.getPatientsById(id);

        // Assert
        if (exists) {
            assertNotNull(result);
            assertEquals(200, result.getStatusCodeValue());
            assertEquals(expectedPatient, result.getBody());
        } else {
            assertNotNull(result);
            assertEquals(404, result.getStatusCodeValue());
            assertNull(result.getBody());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "John,true",
            "Jane,false"
    })
    void testGetPatientsByName(String name, boolean exists) {
        // Arrange
        List<PatientsDTO> expectedPatients = List.of(new PatientsDTO());

        if (exists) {
            when(patientService.getPatientsByName(name)).thenReturn(ResponseEntity.ok(expectedPatients));
        } else {
            when(patientService.getPatientsByName(name)).thenReturn(ResponseEntity.notFound().build());
        }

        // Act
        ResponseEntity<List<PatientsDTO>> result = patientsController.getPatientsByName(name);

        // Assert
        if (exists) {
            assertNotNull(result);
            assertEquals(200, result.getStatusCodeValue());
            assertEquals(expectedPatients, result.getBody());
        } else {
            assertNotNull(result);
            assertEquals(404, result.getStatusCodeValue());
            assertNull(result.getBody());
        }
    }

    @Test
    void testAddPatient() {
        // Arrange
        PatientsDTO patientDTO = new PatientsDTO();
        patientDTO.setPatientName("John Doe");
        patientDTO.setAge(30);
        patientDTO.setGender('M');
        patientDTO.setPhoneNumber("123456789");
        String expectedResult = "Patient added successfully";
        when(patientService.addPatient(patientDTO)).thenReturn(expectedResult);

        // Act
        String result = patientsController.addPatient(patientDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdatePatient() {
        // Arrange
        PatientsDTO patientDTO = new PatientsDTO();
        patientDTO.setPatientId(1);
        patientDTO.setPatientName("John Updated");
        patientDTO.setAge(35);
        patientDTO.setGender('M');
        patientDTO.setPhoneNumber("987654321");
        String expectedResult = "Patient updated successfully";
        when(patientService.updatePatient(patientDTO)).thenReturn(expectedResult);

        // Act
        String result = patientsController.updatePatient(patientDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    void testDeletePatient() {
        // Arrange
        int patientId = 1;
        String expectedResult = "Patient deleted successfully";
        when(patientService.deletePatient(patientId)).thenReturn(expectedResult);

        // Act
        String result = patientsController.deletePatient(patientId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }
}