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

    @GetMapping("/getAppointments")
    public List<AppointmentsDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/getAppointmentsById")
    public ResponseEntity<AppointmentsDTO> getAppointmentsById(@RequestParam int id) {
        return appointmentService.getAppointmentsById(id);
    }

    @GetMapping("/getAppointmentsByDoctorIdAndAppointmentDate")
    public ResponseEntity<AppointmentsDTO> getAppointmentsByDoctorIdAndAppointmentId(@RequestParam int doctorId, @RequestParam String datestamp) {
        return appointmentService.getAppointmentsByDoctorIdAndAppointmentId(doctorId, datestamp);
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentsDTO appointmentsDTO) {
        return appointmentService.createAppointment(appointmentsDTO);
    }

    @PutMapping("/updateAppointment")
    public String updateAppointment(@RequestBody AppointmentsDTO appointmentsDTO) {
        return appointmentService.updateAppointment(appointmentsDTO);
    }

    @DeleteMapping("/deleteAppointment")
    public String deleteAppointment(@RequestParam int id) {
        return appointmentService.deleteAppointment(id);
    }

}