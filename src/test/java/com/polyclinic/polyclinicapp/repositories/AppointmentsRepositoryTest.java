package com.polyclinic.polyclinicapp.repositories;

import com.polyclinic.polyclinicapp.entity.Appointments;
import com.polyclinic.polyclinicapp.entity.Doctors;
import com.polyclinic.polyclinicapp.entity.Patients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class AppointmentsRepositoryTest {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByAppointmentDateAndDoctorIdAndPatientId() {
        // Arrange

        Doctors doctor = new Doctors();
        doctor.setDoctorName("Dr. John Smith");
        doctor.setSpecialization("Cardiology");
        doctor.setFees(1500);
        entityManager.persist(doctor);
        Patients patient = new Patients();
        patient.setPatientName("John Smith");
        patient.setAge(25);
        patient.setGender('M');
        patient.setPhoneNumber("123456789");
        entityManager.persist(patient);

        Appointments appointment1 = new Appointments();
        appointment1.setAppointmentDate(java.sql.Date.valueOf("2023-10-01"));
        appointment1.setDoctorId(doctor.getDoctorId());
        appointment1.setPatientId(patient.getPatientId());
        entityManager.persist(appointment1);

        Appointments appointment2 = new Appointments();
        appointment2.setAppointmentDate(java.sql.Date.valueOf("2023-10-02"));
        appointment2.setDoctorId(doctor.getDoctorId());
        appointment2.setPatientId(patient.getPatientId());
        entityManager.persist(appointment2);

        entityManager.flush();

        // Act
        Appointments foundAppointment = appointmentsRepository.findByAppointmentDateAndDoctorIdAndPatientId(
                java.sql.Date.valueOf("2023-10-01"), appointment1.getDoctorId(), appointment1.getPatientId());

        // Assert
        assertNotNull(foundAppointment);
        assertEquals(appointment1.getAppointmentId(), foundAppointment.getAppointmentId());

        // Test case where no appointment is found
        Appointments notFoundAppointment = appointmentsRepository.findByAppointmentDateAndDoctorIdAndPatientId(
                java.sql.Date.valueOf("2023-10-03"), appointment2.getDoctorId(), appointment2.getPatientId());
        assertNull(notFoundAppointment);
    }

    @Test
    void testFindByDoctorIdAndAppointmentDate() {
        // Arrange
        Doctors doctor = new Doctors();
        doctor.setDoctorName("Dr. John Smith");
        doctor.setSpecialization("Cardiology");
        doctor.setFees(1500);
        entityManager.persist(doctor);

        Patients patient = new Patients();
        patient.setPatientName("John Smith");
        patient.setAge(25);
        patient.setGender('M');
        patient.setPhoneNumber("123456789");
        entityManager.persist(patient);

        Appointments appointment1 = new Appointments();
        appointment1.setAppointmentDate(java.sql.Date.valueOf("2023-10-01"));
        appointment1.setDoctorId(doctor.getDoctorId());
        appointment1.setPatientId(patient.getPatientId());
        entityManager.persist(appointment1);

        Appointments appointment2 = new Appointments();
        appointment2.setAppointmentDate(java.sql.Date.valueOf("2023-10-02"));
        appointment2.setDoctorId(doctor.getDoctorId());
        appointment2.setPatientId(patient.getPatientId());
        entityManager.persist(appointment2);

        entityManager.flush();

        // Act
        Appointments foundAppointment = appointmentsRepository.findByDoctorIdAndAppointmentDate(
                appointment1.getDoctorId(), java.sql.Date.valueOf("2023-10-01"));

        // Assert
        assertNotNull(foundAppointment);
        assertEquals(appointment1.getAppointmentId(), foundAppointment.getAppointmentId());

        // Test case where no appointment is found
        Appointments notFoundAppointment = appointmentsRepository.findByDoctorIdAndAppointmentDate(
                appointment2.getDoctorId(), java.sql.Date.valueOf("2023-10-03"));
        assertNull(notFoundAppointment);
    }
}