package com.polyclinic.polyclinicapp.services;

import com.polyclinic.polyclinicapp.dto.PatientsDTO;
import com.polyclinic.polyclinicapp.ModelMapping;
import com.polyclinic.polyclinicapp.repositories.PatientsRepository;
import com.polyclinic.polyclinicapp.entity.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientsRepository patientsRepository;
    @Autowired
    private ModelMapping modelMapping;

    public List<PatientsDTO> getAllPatients() {
        List<PatientsDTO> patientsList;
        List<Patients> patients = patientsRepository.findAll();
        patientsList = modelMapping.patientsDTOList(patients);
        return patientsList;
    }

    public ResponseEntity<PatientsDTO> getPatientsById(int id) {
        Optional<Patients> patient = patientsRepository.findById(id);
        if (patient.isPresent()) {
            PatientsDTO patientObj = modelMapping.patientsDTO(patient.get());
            return new ResponseEntity<>(patientObj, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<PatientsDTO>> getPatientsByName(String name) {
        List<PatientsDTO> patientsList = modelMapping.patientsDTOList(patientsRepository.findByPatientNameContaining(name));
        if (!patientsList.isEmpty()) {
            return new ResponseEntity<>(patientsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public String addPatient(PatientsDTO patientsDTO) {
        if (patientsDTO.getPatientName() == null || patientsDTO.getPatientName().isEmpty()) {
            return "Patient name cannot be empty";
        } else if (patientsDTO.getAge() <= 0) {
            return "Age must be greater than 0";
        } else if (patientsDTO.getGender() != 'M' && patientsDTO.getGender() != 'F') {
            return "Gender must be 'M' or 'F'";
        } else if(patientsDTO.getGender() == '\u0000') {
            return "Gender cannot be empty";
        } else if (patientsDTO.getPhoneNumber() == null || patientsDTO.getPhoneNumber().isEmpty()) {
            return "Phone number cannot be empty";
        } else {
            patientsRepository.save(modelMapping.patients(patientsDTO));
            return "Patient added successfully";
        }
    }

    public String updatePatient(PatientsDTO patientsDTO) {
        if (!patientsRepository.existsById(patientsDTO.getPatientId())) {
            return "Patient not found";
        } else if (patientsDTO.getPatientName() == null || patientsDTO.getPatientName().isEmpty()) {
            return "Patient name cannot be empty";
        } else if (patientsDTO.getAge() <= 0) {
            return "Age must be greater than 0";
        } else if (patientsDTO.getGender() != 'M' && patientsDTO.getGender() != 'F') {
            return "Gender must be 'M' or 'F'";
        } else if(patientsDTO.getGender() == '\u0000') {
            return "Gender cannot be empty";
        } else if (patientsDTO.getPhoneNumber() == null || patientsDTO.getPhoneNumber().isEmpty()) {
            return "Phone number cannot be empty";
        } else {
            patientsRepository.save(modelMapping.patients(patientsDTO));
            return "Patient updated successfully";
        }
    }

    public String deletePatient(int id) {
        if (id <= 0) {
            return "Invalid patient ID";
        } else if (!patientsRepository.existsById(id)) {
            return "Patient not found";
        } else {
            patientsRepository.deleteById(id);
            return "Patient deleted successfully";
        }
    }

}