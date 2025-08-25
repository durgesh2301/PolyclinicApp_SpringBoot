# Polyclinic Management System

A comprehensive web application for managing a polyclinic's operations, including appointments, doctors, and patients.

## Features

- **Appointment Management**: Schedule, update, and cancel appointments
- **Doctor Management**: Manage doctor profiles, specializations, and availability
- **Patient Management**: Register patients, update patients, delete patient and track appointment history

## Technology Stack

- **Backend**: Java with Spring Boot
- **Database**: MySQL
- **API**: RESTful web services
- **Testing**: JUnit 5, Mockito

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### Installation

1. Clone the repository
   ```bash
   git clone durgesh2301/PolyclinicApp_SpringBoot
   cd PolyclinicApp_Spring_Boot
   ```

2. Configure the database
   ```
   # src/main/resources/application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/polyclinicdb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build the application
   ```bash
   mvn clean install
   ```

4. Run the application
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### Appointments

- `GET /api/appointments` - Get all appointments
- `GET /api/appointments/{id}` - Get appointment by ID
- `GET /api/appointments/doctor/{doctorId}/date` - Get appointments by doctor ID and date
- `POST /api/appointments/createAppointment` - Create a new appointment
- `PUT /api/appointments/{id}` - Update an appointment
- `DELETE /api/appointments/{id}` - Delete an appointment

### Doctors

- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/{id}` - Get doctor by ID
- `POST /api/doctors` - Add a new doctor
- `PUT /api/doctors/{id}` - Update a doctor
- `DELETE /api/doctors/{id}` - Delete a doctor

### Patients

- `GET /api/patients` - Get all patients
- `GET /api/patients/{id}` - Get patient by ID
- `GET /api/patients/{id}/appointments` - Get patient's appointment history
- `POST /api/patients` - Register a new patient
- `PUT /api/patients/{id}` - Update patient information
- `DELETE /api/patients/{id}` - Delete a patient

## Database Schema

The application uses the following main entities:

- **Doctors**: Stores information about healthcare providers
- **Patients**: Contains patient registration and contact details
- **Appointments**: Manages the scheduling between doctors and patients

## Testing

Run the tests using Maven:

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Spring Boot documentation
- MySQL documentation
- All contributors who have helped shape this project
