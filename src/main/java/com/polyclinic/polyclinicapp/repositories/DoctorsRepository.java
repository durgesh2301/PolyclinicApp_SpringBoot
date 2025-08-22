package com.polyclinic.polyclinicapp.repositories;

import com.polyclinic.polyclinicapp.entity.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctors, Integer> {

    List<Doctors> findByDoctorNameContaining(String doctorName);
}