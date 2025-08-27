package com.polyclinic.polyclinicapp.repositories;

import com.polyclinic.polyclinicapp.entity.Doctors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class DoctorsRepositoryTest {

    @Autowired
    private DoctorsRepository doctorsRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByDoctorNameContaining() {
        // Arrange
        Doctors doctor1 = new Doctors();
        doctor1.setDoctorName("Dr. John Smith");
        doctor1.setSpecialization("Cardiology");
        doctor1.setFees(1500);
        entityManager.persist(doctor1);

        Doctors doctor2 = new Doctors();
        doctor2.setDoctorName("Dr. Jane Smith");
        doctor2.setSpecialization("Neurology");
        doctor2.setFees(2000);
        entityManager.persist(doctor2);

        Doctors doctor3 = new Doctors();
        doctor3.setDoctorName("Dr. David Johnson");
        doctor3.setSpecialization("Orthopedics");
        doctor3.setFees(1800);
        entityManager.persist(doctor3);

        entityManager.flush();

        // Act
        List<Doctors> foundDoctors = doctorsRepository.findByDoctorNameContaining("Smith");

        // Assert
        assertEquals(2, foundDoctors.size());
        assertTrue(foundDoctors.stream().anyMatch(d -> d.getDoctorName().equals("Dr. John Smith")));
        assertTrue(foundDoctors.stream().anyMatch(d -> d.getDoctorName().equals("Dr. Jane Smith")));
        assertFalse(foundDoctors.stream().anyMatch(d -> d.getDoctorName().equals("Dr. David Johnson")));
    }
}