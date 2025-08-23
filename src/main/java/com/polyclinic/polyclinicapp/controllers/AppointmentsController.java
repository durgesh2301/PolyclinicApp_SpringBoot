package com.polyclinic.polyclinicapp.controllers;

import com.polyclinic.polyclinicapp.dto.AppointmentsDTO;
import com.polyclinic.polyclinicapp.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AppointmentsController {

    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/api/appointments")
    public List<AppointmentsDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/api/appointments/{id}")
    public ResponseEntity<AppointmentsDTO> getAppointmentsById(@RequestParam int id) {
        return appointmentService.getAppointmentsById(id);
    }

    @GetMapping("/api/appointments/search")
    public ResponseEntity<AppointmentsDTO> getAppointmentsByDoctorIdAndAppointmentId(@RequestParam int doctorId, @RequestParam String datestamp) {
        return appointmentService.getAppointmentsByDoctorIdAndAppointmentId(doctorId, datestamp);
    }

    @PostMapping("/api/appointments")
    public String createAppointment(@RequestBody AppointmentsDTO appointmentsDTO) {
        return appointmentService.createAppointment(appointmentsDTO);
    }

    @PutMapping("/api/appointments")
    public String updateAppointment(@RequestBody AppointmentsDTO appointmentsDTO) {
        return appointmentService.updateAppointment(appointmentsDTO);
    }

    @DeleteMapping("/api/appointments{id}")
    public String deleteAppointment(@RequestParam int id) {
        return appointmentService.deleteAppointment(id);
    }

}