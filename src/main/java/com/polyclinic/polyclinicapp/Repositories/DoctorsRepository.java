package com.polyclinic.polyclinicapp.Repositories;

import com.polyclinic.polyclinicapp.entity.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctors, Integer> {
}
