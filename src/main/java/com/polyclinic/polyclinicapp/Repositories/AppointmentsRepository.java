package com.polyclinic.polyclinicapp.Repositories;

import com.polyclinic.polyclinicapp.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
}
