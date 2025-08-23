package com.polyclinic.polyclinicapp.controllers;

import com.polyclinic.polyclinicapp.dto.PatientsDTO;
import com.polyclinic.polyclinicapp.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PatientsController {

    @Autowired
    PatientService patientService;

    @GetMapping("/api/patients")
    public List<PatientsDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/api/patients/{id}")
    public ResponseEntity<PatientsDTO> getPatientsById(@RequestParam int id) {
        return patientService.getPatientsById(id);
    }

    @GetMapping("/api/patients/{name}")
    public ResponseEntity<List<PatientsDTO>> getPatientsByName(@RequestParam String name) {
        return patientService.getPatientsByName(name);
    }

    @PostMapping("/api/patients")
    public String addPatient(@RequestBody PatientsDTO patientsDTO) {
        return patientService.addPatient(patientsDTO);
    }

    @PutMapping("/api/patients")
    public String updatePatient(@RequestBody PatientsDTO patientsDTO) {
        return patientService.updatePatient(patientsDTO);
    }

    @DeleteMapping("/api/patients/{id}")
    public String deletePatient(@RequestParam int id) {
        return patientService.deletePatient(id);
    }

}