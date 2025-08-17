package com.polyclinic.polyclinicapp.Controllers;

import com.polyclinic.polyclinicapp.DTO.AppointmentsDTO;
import com.polyclinic.polyclinicapp.ModelMapping;
import com.polyclinic.polyclinicapp.Repositories.AppointmentsRepository;
import com.polyclinic.polyclinicapp.entity.Appointments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AppointmentsController {
    @Autowired
    AppointmentsRepository appointmentsRepository;
    @Autowired
    ModelMapping modelMapping;

    @GetMapping("/getAppointments")
    public List<AppointmentsDTO> getAllAppointments() {
        return modelMapping.appointmentsDTOList(appointmentsRepository.findAll()) ;
    }

    @GetMapping("/getAppointmentsById")
    public ResponseEntity<AppointmentsDTO> getAppointmentsById(@RequestParam int id) {
        Optional<Appointments> appointments = appointmentsRepository.findById(id);
        if (appointments.isPresent()) {
            AppointmentsDTO appointmentsDTO = modelMapping.appointmentsDTO(appointments.get());
            return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAppointment")
    public String deleteAppointment(@RequestParam int id) {
        if (id <= 0) {
            return "Invalid appointment ID";
        } else if (!appointmentsRepository.existsById(id)) {
            return "Appointment not found";
        } else {
            appointmentsRepository.deleteById(id);
            return "Appointment deleted successfully";
        }
    }
}