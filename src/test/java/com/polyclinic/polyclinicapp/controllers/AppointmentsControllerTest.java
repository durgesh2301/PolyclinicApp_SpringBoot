package com.polyclinic.polyclinicapp.controllers;

import com.polyclinic.polyclinicapp.dto.AppointmentsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import com.polyclinic.polyclinicapp.services.AppointmentService;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentsControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentsController appointmentsController;

    @Test
    void testGetAllAppointments() {
        // Arrange
        List<AppointmentsDTO> expectedAppointments = List.of(new AppointmentsDTO());
        when(appointmentService.getAllAppointments()).thenReturn(expectedAppointments);

        // Act
        List<AppointmentsDTO> result = appointmentsController.getAllAppointments();

        // Assert
        assertNotNull(result);
        assertEquals(expectedAppointments, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1,true",   // appointment exists
            "2,false"   // appointment does not exist
    })
    void testGetAppointmentsById(int id, boolean exists) {
        // Arrange
        AppointmentsDTO expectedAppointment = new AppointmentsDTO();

        if (exists) {
            when(appointmentService.getAppointmentsById(id)).thenReturn(ResponseEntity.ok(expectedAppointment));
        } else {
            when(appointmentService.getAppointmentsById(id)).thenReturn(ResponseEntity.notFound().build());
        }

        // Act
        ResponseEntity<AppointmentsDTO> result = appointmentsController.getAppointmentsById(id);

        // Assert
        if (exists) {
            assertNotNull(result);
            assertEquals(200, result.getStatusCodeValue());
            assertEquals(expectedAppointment, result.getBody());
        } else {
            assertNotNull(result);
            assertEquals(404, result.getStatusCodeValue());
            assertNull(result.getBody());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1,2023-10-01,true",   // appointment exists
            "2,2025-01-01,false"   // appointment does not exist
    })
    void testGetAppointmentsByDoctorIdAndAppointmentId(int id, String datestamp, boolean exists) {
        // Arrange
        AppointmentsDTO expectedAppointment = new AppointmentsDTO();

        if (exists) {
            when(appointmentService.getAppointmentsByDoctorIdAndAppointmentDate(id, datestamp)).thenReturn(ResponseEntity.ok(expectedAppointment));
        } else {
            when(appointmentService.getAppointmentsByDoctorIdAndAppointmentDate(id, datestamp)).thenReturn(ResponseEntity.notFound().build());
        }

        // Act
        ResponseEntity<AppointmentsDTO> result = appointmentsController.getAppointmentsByDoctorIdAndAppointmentDate(id, datestamp);

        // Assert
        if (exists) {
            assertNotNull(result);
            assertEquals(200, result.getStatusCodeValue());
            assertEquals(expectedAppointment, result.getBody());
        } else {
            assertNotNull(result);
            assertEquals(404, result.getStatusCodeValue());
            assertNull(result.getBody());
        }
    }

    @Test
    void testCreateAppointment() {
        // Arrange
        AppointmentsDTO appointmentDTO = new AppointmentsDTO();
        appointmentDTO.setDoctorId(1);
        appointmentDTO.setPatientId(2);
        appointmentDTO.setAppointmentDate(java.sql.Date.valueOf(LocalDate.now().plusDays(1)));
        String expectedResult = "Appointment created successfully";
        when(appointmentService.createAppointment(appointmentDTO)).thenReturn(expectedResult);

        // Act
        String result = appointmentsController.createAppointment(appointmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    void testCreateAppointment_WithPastDate() {
        // Arrange
        AppointmentsDTO appointmentDTO = new AppointmentsDTO();
        appointmentDTO.setDoctorId(1);
        appointmentDTO.setPatientId(2);
        appointmentDTO.setAppointmentDate(java.sql.Date.valueOf(LocalDate.now().minusDays(1))); // Past date

        when(appointmentService.createAppointment(appointmentDTO)).thenReturn("Appointment date cannot be in the past");

        // Act & Assert
        String result = appointmentsController.createAppointment(appointmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Appointment date cannot be in the past", result);
    }

    @Test
    void testUpdateAppointment() {
        // Arrange
        AppointmentsDTO appointmentDTO = new AppointmentsDTO();
        appointmentDTO.setAppointmentId(1);
        appointmentDTO.setDoctorId(1);
        appointmentDTO.setPatientId(2);
        appointmentDTO.setAppointmentDate(java.sql.Date.valueOf(LocalDate.now().plusDays(1)));
        String expectedResult = "Appointment updated successfully";
        when(appointmentService.updateAppointment(appointmentDTO)).thenReturn(expectedResult);

        // Act
        String result = appointmentsController.updateAppointment(appointmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    void testDeleteAppointment() {
        // Arrange
        int appointmentId = 1;
        String expectedResult = "Appointment deleted successfully";
        when(appointmentService.deleteAppointment(appointmentId)).thenReturn(expectedResult);

        // Act
        String result = appointmentsController.deleteAppointment(appointmentId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }
}