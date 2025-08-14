package com.polyclinic.polyclinicapp.Repositories;

import com.polyclinic.polyclinicapp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
