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
    private AppointmentsRepository appointmentsRepository;
    @Autowired
    private PatientsRepository patientsRepository;
    @Autowired
    private DoctorsRepository doctorsRepository;
    @Autowired
    private ModelMapping modelMapping;

    public List<AppointmentsDTO> getAllAppointments() {
        List<AppointmentsDTO> appointmentsList;
        List<Appointments> appointments = appointmentsRepository.findAll();
        appointmentsList = modelMapping.appointmentsDTOList(appointments);
        return appointmentsList;
    }

    public ResponseEntity<AppointmentsDTO> getAppointmentsById(int id) {
        Optional<Appointments> appointments = appointmentsRepository.findById(id);
        if (appointments.isPresent()) {
            AppointmentsDTO appointmentsobj = modelMapping.appointmentsDTO(appointments.get());
            return new ResponseEntity<>(appointmentsobj, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<AppointmentsDTO> getAppointmentsByDoctorIdAndAppointmentDate(int doctorId, String datestamp) {
        Date appointmentDate = java.sql.Date.valueOf(datestamp);
        Appointments appointments = appointmentsRepository.findByDoctorIdAndAppointmentDate(doctorId, appointmentDate);
        if (appointments != null) {
            AppointmentsDTO appointmentsobj = modelMapping.appointmentsDTO(appointments);
            return new ResponseEntity<>(appointmentsobj, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public String createAppointment(AppointmentsDTO appointmentsDTO) {

        if(patientsRepository.findById(appointmentsDTO.getPatientId()).isEmpty()) {
            return "Patient not found";
        }
        else if (doctorsRepository.findById(appointmentsDTO.getDoctorId()).isEmpty()) {
            return "Doctor not found";
        }
        else if(appointmentsDTO.getAppointmentDate().before(new Date())) {
            return "Appointment date must be in the future";
        }
        else if (appointmentsRepository.findByAppointmentDateAndDoctorIdAndPatientId(appointmentsDTO.getAppointmentDate(), appointmentsDTO.getDoctorId(), appointmentsDTO.getPatientId()) != null) {
            return "Appointment already exists";
        }
        else {
            appointmentsRepository.save(modelMapping.appointments(appointmentsDTO));
            return "Appointment created successfully";
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