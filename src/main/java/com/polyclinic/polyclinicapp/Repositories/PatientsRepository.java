package com.polyclinic.polyclinicapp.Repositories;

import com.polyclinic.polyclinicapp.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer> {
}
