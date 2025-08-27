package com.polyclinic.polyclinicapp.controllers;

import com.polyclinic.polyclinicapp.dto.DoctorsDTO;
import com.polyclinic.polyclinicapp.services.DoctorService;
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
class DoctorsControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorsController doctorsController;

    @Test
    void testGetAllDoctors() {
        // Arrange
        List<DoctorsDTO> expectedDoctors = List.of(new DoctorsDTO());
        when(doctorService.getAllDoctors()).thenReturn(expectedDoctors);

        // Act
        List<DoctorsDTO> result = doctorsController.getAllDoctors();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDoctors, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1,true",   // doctor exists
            "2,false"   // doctor does not exist
    })
    void testGetDoctorsById(int id, boolean exists) {
        // Arrange
        DoctorsDTO expectedDoctor = new DoctorsDTO();

        if (exists) {
            when(doctorService.getDoctorsById(id)).thenReturn(ResponseEntity.ok(expectedDoctor));
        } else {
            when(doctorService.getDoctorsById(id)).thenReturn(ResponseEntity.notFound().build());
        }

        // Act
        ResponseEntity<DoctorsDTO> result = doctorsController.getDoctorsById(id);

        // Assert
        if (exists) {
            assertNotNull(result);
            assertEquals(200, result.getStatusCodeValue());
            assertEquals(expectedDoctor, result.getBody());
        } else {
            assertNotNull(result);
            assertEquals(404, result.getStatusCodeValue());
            assertNull(result.getBody());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "John,true",   // doctor exists
            "Jane,false"   // doctor does not exist
    })
    void testGetDoctorsByName(String name, boolean exists) {
        // Arrange
        List<DoctorsDTO> expectedDoctors = List.of(new DoctorsDTO());

        if (exists) {
            when(doctorService.getDoctorsByName(name)).thenReturn(ResponseEntity.ok(expectedDoctors));
        } else {
            when(doctorService.getDoctorsByName(name)).thenReturn(ResponseEntity.notFound().build());
        }

        // Act
        ResponseEntity<List<DoctorsDTO>> result = doctorsController.getDoctorsByName(name);

        // Assert
        if (exists) {
            assertNotNull(result);
            assertEquals(200, result.getStatusCodeValue());
            assertEquals(expectedDoctors, result.getBody());
        } else {
            assertNotNull(result);
            assertEquals(404, result.getStatusCodeValue());
            assertNull(result.getBody());
        }
    }

    @Test
    void testAddDoctor() {
        // Arrange
        DoctorsDTO doctorDTO = new DoctorsDTO();
        doctorDTO.setDoctorName("Dr. Smith");
        doctorDTO.setSpecialization("Cardiology");
        doctorDTO.setFees(1500);
        String expectedResult = "Doctor added successfully";
        when(doctorService.addDoctor(doctorDTO)).thenReturn(expectedResult);

        // Act
        String result = doctorsController.addDoctor(doctorDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateDoctor() {
        // Arrange
        DoctorsDTO doctorDTO = new DoctorsDTO();
        doctorDTO.setDoctorId(1);
        doctorDTO.setDoctorName("Dr. Updated");
        doctorDTO.setSpecialization("Updated Specialty");
        doctorDTO.setFees(2000);
        String expectedResult = "Doctor updated successfully";
        when(doctorService.updateDoctor(doctorDTO)).thenReturn(expectedResult);

        // Act
        String result = doctorsController.updateDoctor(doctorDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    void testDeleteDoctor() {
        // Arrange
        int doctorId = 1;
        String expectedResult = "Doctor deleted successfully";
        when(doctorService.deleteDoctor(doctorId)).thenReturn(expectedResult);

        // Act
        String result = doctorsController.deleteDoctor(doctorId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }
}