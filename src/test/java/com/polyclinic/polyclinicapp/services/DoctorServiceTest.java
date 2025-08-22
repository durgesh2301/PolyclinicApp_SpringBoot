package com.polyclinic.polyclinicapp.services;

import com.polyclinic.polyclinicapp.ModelMapping;
import com.polyclinic.polyclinicapp.dto.DoctorsDTO;
import com.polyclinic.polyclinicapp.entity.Doctors;
import com.polyclinic.polyclinicapp.repositories.DoctorsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorsRepository doctorsRepository;
    @Mock
    private ModelMapping modelMapping;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void testGetAllDoctors() {
        List<Doctors> doctorsList = List.of(new Doctors());
        when(doctorsRepository.findAll()).thenReturn(doctorsList);
        when(modelMapping.doctorsDTOList(doctorsList)).thenReturn(List.of(new DoctorsDTO()));

        List<DoctorsDTO> result = doctorService.getAllDoctors();

        Assertions.assertFalse(result.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,true",   // doctor exists
            "2,false"   // doctor does not exist
    })
    void testGetDoctorsById(int id, boolean exists) {
        Doctors doctor = new Doctors();
        DoctorsDTO doctorDTO = new DoctorsDTO();

        if (exists) {
            when(doctorsRepository.findById(id)).thenReturn(Optional.of(doctor));
            when(modelMapping.doctorsDTO(doctor)).thenReturn(doctorDTO);
        } else {
            when(doctorsRepository.findById(id)).thenReturn(Optional.empty());
        }

        ResponseEntity<DoctorsDTO> result = doctorService.getDoctorsById(id);

        if (exists) {
            Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
            Assertions.assertEquals(doctorDTO, result.getBody());
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
    void testGetDoctorsByName(String name, boolean exists) {
        List<Doctors> doctors = List.of(new Doctors());
        List<DoctorsDTO> doctorsList = List.of(new DoctorsDTO());

        if (exists) {
            when(doctorsRepository.findByDoctorNameContaining(name)).thenReturn(doctors);
            when(modelMapping.doctorsDTOList(doctors)).thenReturn(doctorsList);
        } else {
            when(doctorsRepository.findByDoctorNameContaining(name)).thenReturn(List.of());
        }

        ResponseEntity<List<DoctorsDTO>> result = doctorService.getDoctorsByName(name);

        if (exists) {
            Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
            Assertions.assertEquals(doctorsList, result.getBody());
        } else {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
            Assertions.assertNull(result.getBody());
        }
    }

    @Test
    void testAddDoctorInvalidName() {
        DoctorsDTO dto = new DoctorsDTO();
        dto.setDoctorName("");
        dto.setFees(100);
        dto.setSpecialization("Cardiology");

        String result = doctorService.addDoctor(dto);

        Assertions.assertEquals("Doctor name cannot be empty", result);
    }

    @Test
    void testUpdateDoctorInvalidId() {
        DoctorsDTO dto = new DoctorsDTO();
        dto.setDoctorId(1);
        when(doctorsRepository.existsById(dto.getDoctorId())).thenReturn(false);

        String result = doctorService.updateDoctor(dto);

        Assertions.assertEquals("Doctor not found", result);
    }

    @Test
    void testDeleteDoctorInvalidId() {
        int id = -1;

        String result = doctorService.deleteDoctor(id);

        Assertions.assertEquals("Invalid doctor ID", result);
    }
}