package com.polyclinic.polyclinicapp.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentId")
    private int appointmentId;

    @Column(name = "PatientId")
    private int patientId;

    @Column(name = "DoctorId")
    private int doctorId;

    @Column(name = "AppointmentDate")
    private Date appointmentDate;

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

    @ManyToOne
    @JoinColumn(name = "PatientId", referencedColumnName = "PatientId", insertable=false, updatable=false)
    private Patients patients;
    @ManyToOne
    @JoinColumn(name = "DoctorId", referencedColumnName = "DoctorId", insertable=false, updatable=false)
    private Doctors doctors;

    public Patients getPatients() {
        return patients;
    }
    public void setPatients(Patients patients) {
        this.patients = patients;
    }
    public Doctors getDoctors() {
        return doctors;
    }
    public void setDoctors(Doctors doctors) {
        this.doctors = doctors;
    }
}