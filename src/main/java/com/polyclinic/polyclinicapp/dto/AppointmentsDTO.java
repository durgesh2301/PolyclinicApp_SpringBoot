package com.polyclinic.polyclinicapp.dto;

import java.util.Date;

public class AppointmentsDTO {

    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Date appointmentDate;
    private PatientsDTO patientsDTO;
    private DoctorsDTO doctorsDTO;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public PatientsDTO getPatientsDTO() {
        return patientsDTO;
    }
    public void setPatientsDTO(PatientsDTO patientsDTO) {
        this.patientsDTO = patientsDTO;
    }
    public DoctorsDTO getDoctorsDTO() {
        return doctorsDTO;
    }
    public void setDoctorsDTO(DoctorsDTO doctorsDTO) {
        this.doctorsDTO = doctorsDTO;
    }

}