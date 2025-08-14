package com.polyclinic.polyclinicapp.Controllers;

import com.polyclinic.polyclinicapp.Repositories.PatientsRepository;
import com.polyclinic.polyclinicapp.entity.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PatientsController {
    @Autowired
    PatientsRepository patientsRepository;

    @GetMapping("/getPatients")
    public List<Patients> getAllPatients() {
        return patientsRepository.findAll();
    }

    @GetMapping("/getPatientsById")
    public ResponseEntity<Patients> getPatientsById(@RequestParam int id) {
        Optional<Patients> patient = patientsRepository.findById(id);
        if (patient.isPresent()) {
            return new ResponseEntity<>(patient.get(), HttpStatus.OK);
        } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getPatientsByName")
    public ResponseEntity<List<Patients>> getPatientsByName(@RequestParam String name) {
        List<Patients> patients = patientsRepository.findAll();
        List<Patients> filteredPatients = patients.stream()
                .filter(patient -> patient.getPatientName().toLowerCase().contains(name.toLowerCase()))
                .toList();
        if (!filteredPatients.isEmpty()) {
            return new ResponseEntity<>(filteredPatients, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addPatient")
    public String  addPatient(Patients patient) {
        if (patient.getPatientName() == null || patient.getPatientName().isEmpty()) {
            return "Patient name cannot be empty";
        }
        if (patient.getAge() <= 0) {
            return "Age must be greater than 0";
        }
        if (patient.getPhoneNumber() == null || patient.getPhoneNumber().isEmpty()) {
            return "Phone number cannot be empty";
        }
        patientsRepository.save(patient);
        return "Patient added successfully";
    }

    @PutMapping("/updatePatient")
    public ResponseEntity<String> updatePatient(Patients patient) {
        if (patient.getPatientId() <= 0) {
            return new ResponseEntity<>("Invalid patient ID", HttpStatus.BAD_REQUEST);
        } else if (!patientsRepository.existsById(patient.getPatientId())) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        } else if (patient.getPatientName() == null || patient.getPatientName().isEmpty()) {
            return new ResponseEntity<>("Patient name cannot be empty", HttpStatus.BAD_REQUEST);
        } else if (patient.getAge() <= 0) {
            return new ResponseEntity<>("Age must be greater than 0", HttpStatus.BAD_REQUEST);
        } else if (patient.getPhoneNumber() == null || patient.getPhoneNumber().isEmpty()) {
            return new ResponseEntity<>("Phone number cannot be empty", HttpStatus.BAD_REQUEST);
        }
        patientsRepository.save(patient);
        return new ResponseEntity<>("Patient updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deletePatient")
    public ResponseEntity<String> deletePatient(@RequestParam int id) {
        if (id <= 0) {
            return new ResponseEntity<>("Invalid patient ID", HttpStatus.BAD_REQUEST);
        } else if (!patientsRepository.existsById(id)) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
        else {
            patientsRepository.deleteById(id);
            return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
        }
    }
}
