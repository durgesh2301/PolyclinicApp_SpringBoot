package com.polyclinic.polyclinicapp.services;

import com.polyclinic.polyclinicapp.ModelMapping;
import com.polyclinic.polyclinicapp.dto.AppointmentsDTO;
import com.polyclinic.polyclinicapp.entity.Appointments;
import com.polyclinic.polyclinicapp.entity.Doctors;
import com.polyclinic.polyclinicapp.entity.Patients;
import com.polyclinic.polyclinicapp.repositories.AppointmentsRepository;
import com.polyclinic.polyclinicapp.repositories.DoctorsRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentsRepository appointmentsRepository;
    @Mock
    private DoctorsRepository doctorsRepository;
    @Mock
    private PatientsRepository patientsRepository;
    @Mock
    private ModelMapping modelMapping;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void testGetAllAppointments() {
        List<Appointments> appointmentsList = List.of(new Appointments());
        when(appointmentsRepository.findAll()).thenReturn(appointmentsList);
        when(modelMapping.appointmentsDTOList(appointmentsList)).thenReturn(List.of(new AppointmentsDTO()));

        List<AppointmentsDTO> result = appointmentService.getAllAppointments();

        Assertions.assertFalse(result.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,true",   // appointment exists
            "2,false"   // appointment does not exist
    })
    void testGetAppointmentsById(int id, boolean exists) {
        Appointments appointment = new Appointments();
        AppointmentsDTO appointmentDTO = new AppointmentsDTO();

        if (exists) {
            when(appointmentsRepository.findById(id)).thenReturn(Optional.of(appointment));
            when(modelMapping.appointmentsDTO(appointment)).thenReturn(appointmentDTO);
        } else {
            when(appointmentsRepository.findById(id)).thenReturn(Optional.empty());
        }

        ResponseEntity<AppointmentsDTO> result = appointmentService.getAppointmentsById(id);

        if (exists) {
            Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
            Assertions.assertEquals(appointmentDTO, result.getBody());
        } else {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
            Assertions.assertNull(result.getBody());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1,2023-10-01,true",   // appointment exists
            "2,2025-01-01,false"   // appointment does not exist
    })
    void testGetAppointmentsByDoctorIdAndAppointmentId(int doctorId,String appointmentDate, boolean exists) {
        Appointments appointment = new Appointments();
        AppointmentsDTO appointmentDTO = new AppointmentsDTO();
        if (exists) {
            when(appointmentsRepository.findByDoctorIdAndAppointmentDate(doctorId, java.sql.Date.valueOf(appointmentDate))).thenReturn(appointment);
            when(modelMapping.appointmentsDTO(appointment)).thenReturn(appointmentDTO);
        }
        else {
            when(appointmentsRepository.findByDoctorIdAndAppointmentDate(doctorId, java.sql.Date.valueOf(appointmentDate))).thenReturn(null);
        }
        ResponseEntity<AppointmentsDTO> result = appointmentService.getAppointmentsByDoctorIdAndAppointmentId(doctorId, appointmentDate);
        if (exists) {
            Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
            Assertions.assertEquals(appointmentDTO, result.getBody());
        } else {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
            Assertions.assertNull(result.getBody());
        }
    }

    @Test
    void testCreateAppointmentInvalidDoctor() {
        AppointmentsDTO dto = new AppointmentsDTO();
        dto.setDoctorId(1);
        dto.setPatientId(1);
        when(patientsRepository.findById(dto.getPatientId())).thenReturn(Optional.of(new Patients()));
        when(doctorsRepository.findById(dto.getDoctorId())).thenReturn(Optional.empty());
        String result = appointmentService.createAppointment(dto);
        Assertions.assertEquals("Doctor not found", result);
    }

    @Test
    void testCreateAppointmentExists() {
        AppointmentsDTO dto = new AppointmentsDTO();
        dto.setPatientId(1);
        dto.setDoctorId(1);
        dto.setAppointmentDate(java.sql.Date.valueOf(LocalDate.now().plusDays(1)));
        when(patientsRepository.findById(dto.getPatientId())).thenReturn(Optional.of(new Patients()));
        when(doctorsRepository.findById(dto.getDoctorId())).thenReturn(Optional.of(new Doctors()));
        when(appointmentsRepository.findByAppointmentDateAndDoctorIdAndPatientId(dto.getAppointmentDate(), dto.getDoctorId(), dto.getPatientId())).thenReturn(new Appointments());
        String result = appointmentService.createAppointment(dto);
        Assertions.assertEquals("Appointment already exists", result);
    }

    @Test
    void testUpdateAppointmentInvalidId() {
        AppointmentsDTO dto = new AppointmentsDTO();
        dto.setAppointmentId(1);
        when(appointmentsRepository.existsById(dto.getAppointmentId())).thenReturn(false);
        String result = appointmentService.updateAppointment(dto);
        Assertions.assertEquals("Appointment not found", result);
    }

    @Test
    void testDeleteAppointmentInvalidId() {
        int id = -1;
        String result = appointmentService.deleteAppointment(id);
        Assertions.assertEquals("Invalid appointment ID", result);
    }
}
