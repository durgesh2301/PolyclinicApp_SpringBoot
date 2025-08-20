package com.polyclinic.polyclinicapp.Controllers;

import com.polyclinic.polyclinicapp.DTO.AppointmentsDTO;
import com.polyclinic.polyclinicapp.ModelMapping;
import com.polyclinic.polyclinicapp.Repositories.*;
import com.polyclinic.polyclinicapp.entity.Appointments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AppointmentsController {
    @Autowired
    AppointmentsRepository appointmentsRepository;
    @Autowired
    PatientsRepository patientsRepository;
    @Autowired
    DoctorsRepository doctorsRepository;
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

    @GetMapping("/getAppointmentsByDoctorIdAndAppointmentDate")
    public ResponseEntity<AppointmentsDTO> getAppointmentsByDoctorIdAndAppointmentId(@RequestParam int doctorId, @RequestParam String datestr) {
        Date appointmentDate = java.sql.Date.valueOf(datestr);
        Appointments appointments = appointmentsRepository.findByDoctorIdAndAppointmentDate(doctorId, appointmentDate);
        if (appointments != null) {
            AppointmentsDTO appointmentsDTO = modelMapping.appointmentsDTO(appointments);
            return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentsDTO appointmentsDTO) {
        Appointments appointments = modelMapping.appointments(appointmentsDTO);
        if(!patientsRepository.findById(appointments.getPatientId()).isPresent()) {
            return new ResponseEntity<>("Patient not found", HttpStatus.BAD_REQUEST);
        }
        else if (!doctorsRepository.findById(appointments.getDoctorId()).isPresent()) {
            return new ResponseEntity<>("Doctor not found", HttpStatus.BAD_REQUEST);
        }
        else if(appointments.getAppointmentDate().before(new Date())) {
            return new ResponseEntity<>("Appointment date must be in the future", HttpStatus.BAD_REQUEST);
        }
        else if (appointmentsRepository.findByAppointmentDateAndDoctorIdAndPatientId(appointments.getAppointmentDate(), appointments.getDoctorId(), appointments.getPatientId()) != null) {
            return new ResponseEntity<>("Appointment already exists", HttpStatus.BAD_REQUEST);
        }
        else {
            appointmentsRepository.save(appointments);
            return new ResponseEntity<>("Appointment created successfully", HttpStatus.CREATED);
        }
    }

    @PutMapping("/updateAppointment")
    public String updateAppointment(@RequestBody AppointmentsDTO appointmentsDTO) {
        if (!appointmentsRepository.existsById(appointmentsDTO.getAppointmentId())) {
            return "Appointment not found";
        } else if (!patientsRepository.findById(appointmentsDTO.getPatientId()).isPresent()) {
            return "Patient not found";
        } else if (!doctorsRepository.findById(appointmentsDTO.getDoctorId()).isPresent()) {
            return "Doctor not found";
        } else if (appointmentsDTO.getAppointmentDate().before(new Date())) {
            return "Appointment date must be in the future";
        } else {;
            appointmentsRepository.save(modelMapping.appointments(appointmentsDTO));
            return "Appointment updated successfully";
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