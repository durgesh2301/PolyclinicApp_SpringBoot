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

    @GetMapping("/getPatients")
    public List<PatientsDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/getPatientsById")
    public ResponseEntity<PatientsDTO> getPatientsById(@RequestParam int id) {
        return patientService.getPatientsById(id);
    }

    @GetMapping("/getPatientsByName")
    public ResponseEntity<List<PatientsDTO>> getPatientsByName(@RequestParam String name) {
        return patientService.getPatientsByName(name);
    }

    @PostMapping("/addPatient")
    public String addPatient(@RequestBody PatientsDTO patientsDTO) {
        return patientService.addPatient(patientsDTO);
    }

    @PutMapping("/updatePatient")
    public String updatePatient(@RequestBody PatientsDTO patientsDTO) {
        return patientService.updatePatient(patientsDTO);
    }

    @DeleteMapping("/deletePatient")
    public String deletePatient(@RequestParam int id) {
        return patientService.deletePatient(id);
    }

}