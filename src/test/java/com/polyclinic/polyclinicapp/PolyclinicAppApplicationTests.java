package com.polyclinic.polyclinicapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.polyclinic.polyclinicapp.controllers.AppointmentsController;
import com.polyclinic.polyclinicapp.controllers.DoctorsController;
import com.polyclinic.polyclinicapp.controllers.PatientsController;
import com.polyclinic.polyclinicapp.repositories.AppointmentsRepository;
import com.polyclinic.polyclinicapp.repositories.DoctorsRepository;
import com.polyclinic.polyclinicapp.repositories.PatientsRepository;
import com.polyclinic.polyclinicapp.services.AppointmentService;
import com.polyclinic.polyclinicapp.services.DoctorService;
import com.polyclinic.polyclinicapp.services.PatientService;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PolyclinicAppApplicationTests {

    @Autowired
    private ApplicationContext context;

	@Test
	void contextLoads() {
	}

    @Test
    void controllerBeansLoad() {
        // Verify that all controller beans are created successfully
        assertThat(context.getBean(AppointmentsController.class)).isNotNull();
        assertThat(context.getBean(DoctorsController.class)).isNotNull();
        assertThat(context.getBean(PatientsController.class)).isNotNull();
    }


    @Test
    void serviceBeansLoad() {
        // Verify that all service beans are created successfully
        assertThat(context.getBean(AppointmentService.class)).isNotNull();
        assertThat(context.getBean(DoctorService.class)).isNotNull();
        assertThat(context.getBean(PatientService.class)).isNotNull();
    }

    @Test
    void repositoryBeansLoad() {
        // Verify that all repository beans are created successfully
        assertThat(context.getBean(AppointmentsRepository.class)).isNotNull();
        assertThat(context.getBean(DoctorsRepository.class)).isNotNull();
        assertThat(context.getBean(PatientsRepository.class)).isNotNull();
    }

    @Test
    void applicationPropertiesLoad() {
        // Test that application properties are loaded correctly
        String serverPort = context.getEnvironment().getProperty("spring.application.name");
        assertThat(serverPort).isNotNull();

        String dbUrl = context.getEnvironment().getProperty("spring.datasource.url");
        assertThat(dbUrl).isNotNull();

        String dbDriver = context.getEnvironment().getProperty("spring.datasource.driver-class-name");
        assertThat(dbDriver).isNotNull();

    }

    @Test
    void entityManagerFactoryInitialized() {
        // Verify that JPA is properly configured
        assertThat(context.getBean("entityManagerFactory")).isNotNull();
    }

}
