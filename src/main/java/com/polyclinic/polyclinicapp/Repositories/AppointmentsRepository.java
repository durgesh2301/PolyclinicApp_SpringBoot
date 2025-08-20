package com.polyclinic.polyclinicapp.Repositories;

import com.polyclinic.polyclinicapp.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {

    public Appointments findByAppointmentDateAndDoctorIdAndPatientId(Date appointmentDate, int doctorId, int patientId);

    public Appointments findByDoctorIdAndAppointmentDate(int doctorId, Date appointmentDate);
}
