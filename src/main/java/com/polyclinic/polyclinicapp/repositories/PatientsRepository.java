package com.polyclinic.polyclinicapp.repositories;

import com.polyclinic.polyclinicapp.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer> {

    List<Patients> findByPatientNameContaining(String patientName);
}