package com.polyclinic.polyclinicapp.repositories;

import com.polyclinic.polyclinicapp.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {

    Appointments findByAppointmentDateAndDoctorIdAndPatientId(Date appointmentDate, int doctorId, int patientId);

    Appointments findByDoctorIdAndAppointmentDate(int doctorId, Date appointmentDate);

}