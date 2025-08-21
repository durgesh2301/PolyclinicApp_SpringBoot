package com.polyclinic.polyclinicapp;

import com.polyclinic.polyclinicapp.dto.*;
import com.polyclinic.polyclinicapp.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapping {

    @Autowired
    ModelMapper modelMapper;

    public PatientsDTO patientsDTO(Patients patients) {
        return modelMapper.map(patients, PatientsDTO.class);
    }

    public  List<PatientsDTO> patientsDTOList(List<Patients> patientsList) {
        return patientsList.stream()
                .map(this::patientsDTO)
                .collect(Collectors.toList());
    }

    public Patients patients(PatientsDTO patientsDTO) {
        return modelMapper.map(patientsDTO, Patients.class);
    }

    public DoctorsDTO doctorsDTO(Doctors doctors) {
        return modelMapper.map(doctors, DoctorsDTO.class);
    }

    public  List<DoctorsDTO> doctorsDTOList(List<Doctors> doctorsList) {
        return doctorsList.stream()
                .map(this::doctorsDTO)
                .collect(Collectors.toList());
    }

    public Doctors doctors(DoctorsDTO doctorsDTO) {
        return modelMapper.map(doctorsDTO, Doctors.class);
    }

    public AppointmentsDTO appointmentsDTO(Appointments appointments) {
        AppointmentsDTO appointmentsDTO = new AppointmentsDTO();
        appointmentsDTO.setAppointmentId(appointments.getAppointmentId());
        appointmentsDTO.setPatientId(appointments.getPatientId());
        appointmentsDTO.setDoctorId(appointments.getDoctorId());
        appointmentsDTO.setAppointmentDate(appointments.getAppointmentDate());
        appointmentsDTO.setPatientsDTO(patientsDTO(appointments.getPatients()));
        appointmentsDTO.setDoctorsDTO(doctorsDTO(appointments.getDoctors()));
        return appointmentsDTO;
    }

    public  List<AppointmentsDTO> appointmentsDTOList(List<Appointments> appointmentsList) {
        return appointmentsList.stream()
                .map(this::appointmentsDTO)
                .collect(Collectors.toList());
    }

    public Appointments appointments(AppointmentsDTO appointmentsDTO) {
        return modelMapper.map(appointmentsDTO, Appointments.class);
    }

}
