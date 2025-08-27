package com.polyclinic.polyclinicapp.repositories;

import com.polyclinic.polyclinicapp.entity.Patients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PatientsRepositoryTest {

    @Autowired
    private PatientsRepository patientsRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByPatientNameContaining() {
        // Arrange
        Patients patient1 = new Patients();
        patient1.setPatientName("John Smith");
        patient1.setAge(25);
        patient1.setGender('M');
        patient1.setPhoneNumber("123456789");
        entityManager.persist(patient1);

        Patients patient2 = new Patients();
        patient2.setPatientName("Jane Smith");
        patient2.setAge(23);
        patient2.setGender('F');
        patient2.setPhoneNumber("987654321");
        entityManager.persist(patient2);

        Patients patient3 = new Patients();
        patient3.setPatientName("David Johnson");
        patient3.setAge(30);
        patient3.setGender('M');
        patient3.setPhoneNumber("1122334455");
        entityManager.persist(patient3);

        entityManager.flush();

        // Act
        List<Patients> foundPatients = patientsRepository.findByPatientNameContaining("Smith");

        // Assert
        assertEquals(2, foundPatients.size());
        assertTrue(foundPatients.stream().anyMatch(p -> p.getPatientName().equals("John Smith")));
        assertTrue(foundPatients.stream().anyMatch(p -> p.getPatientName().equals("Jane Smith")));
        assertFalse(foundPatients.stream().anyMatch(p -> p.getPatientName().equals("David Johnson")));
    }
}
