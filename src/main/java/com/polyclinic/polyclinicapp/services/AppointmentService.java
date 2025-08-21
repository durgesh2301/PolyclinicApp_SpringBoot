package com.polyclinic.polyclinicapp.services;

import com.polyclinic.polyclinicapp.dto.AppointmentsDTO;
import com.polyclinic.polyclinicapp.ModelMapping;
import com.polyclinic.polyclinicapp.repositories.*;
import com.polyclinic.polyclinicapp.entity.Appointments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    AppointmentsRepository appointmentsRepository;
    @Autowired
    PatientsRepository patientsRepository;
    @Autowired
    DoctorsRepository doctorsRepository;
    @Autowired
    ModelMapping modelMapping;

    public List<AppointmentsDTO> getAllAppointments() {
        return modelMapping.appointmentsDTOList(appointmentsRepository.findAll()) ;
    }

    public ResponseEntity<AppointmentsDTO> getAppointmentsById(int id) {
        Optional<Appointments> appointments = appointmentsRepository.findById(id);
        if (appointments.isPresent()) {
            AppointmentsDTO appointmentsDTO = modelMapping.appointmentsDTO(appointments.get());
            return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<AppointmentsDTO> getAppointmentsByDoctorIdAndAppointmentId(int doctorId, String datestamp) {
        Date appointmentDate = java.sql.Date.valueOf(datestamp);
        Appointments appointments = appointmentsRepository.findByDoctorIdAndAppointmentDate(doctorId, appointmentDate);
        if (appointments != null) {
            AppointmentsDTO appointmentsDTO = modelMapping.appointmentsDTO(appointments);
            return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> createAppointment(AppointmentsDTO appointmentsDTO) {
        Appointments appointments = modelMapping.appointments(appointmentsDTO);
        if(patientsRepository.findById(appointments.getPatientId()).isEmpty()) {
            return new ResponseEntity<>("Patient not found", HttpStatus.BAD_REQUEST);
        }
        else if (doctorsRepository.findById(appointments.getDoctorId()).isEmpty()) {
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

    public String updateAppointment(AppointmentsDTO appointmentsDTO) {
        if (!appointmentsRepository.existsById(appointmentsDTO.getAppointmentId())) {
            return "Appointment not found";
        } else if (patientsRepository.findById(appointmentsDTO.getPatientId()).isEmpty()) {
            return "Patient not found";
        } else if (doctorsRepository.findById(appointmentsDTO.getDoctorId()).isEmpty()) {
            return "Doctor not found";
        } else if (appointmentsDTO.getAppointmentDate().before(new Date())) {
            return "Appointment date must be in the future";
        } else {
            appointmentsRepository.save(modelMapping.appointments(appointmentsDTO));
            return "Appointment updated successfully";
        }
    }

    public String deleteAppointment(int id) {
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