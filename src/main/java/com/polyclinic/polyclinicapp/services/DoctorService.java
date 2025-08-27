package com.polyclinic.polyclinicapp.services;

import com.polyclinic.polyclinicapp.dto.DoctorsDTO;
import com.polyclinic.polyclinicapp.ModelMapping;
import com.polyclinic.polyclinicapp.repositories.DoctorsRepository;
import com.polyclinic.polyclinicapp.entity.Doctors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorsRepository doctorsRepository;
    @Autowired
    private ModelMapping modelMapping;

    public List<DoctorsDTO> getAllDoctors() {
        List<DoctorsDTO> doctorsList;
        List<Doctors> doctors = doctorsRepository.findAll();
        doctorsList = modelMapping.doctorsDTOList(doctors);
        return doctorsList;
    }

    public ResponseEntity<DoctorsDTO> getDoctorsById(int id) {
        Optional<Doctors> doctor = doctorsRepository.findById(id);
        if (doctor.isPresent()) {
            DoctorsDTO doctorObj =  modelMapping.doctorsDTO(doctor.get());
            return new ResponseEntity<>(doctorObj, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null ,HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<DoctorsDTO>> getDoctorsByName(String name) {
        List<DoctorsDTO> doctorsList = modelMapping.doctorsDTOList(doctorsRepository.findByDoctorNameContaining(name));
        if (!doctorsList.isEmpty()) {
            return new ResponseEntity<>(doctorsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public String addDoctor(DoctorsDTO doctorsDTO) {
        if (doctorsDTO.getDoctorName() == null || doctorsDTO.getDoctorName().isEmpty()) {
            return "Doctor name cannot be empty";
        } else if (doctorsDTO.getFees() <= 0) {
            return "Fees must be greater than 0";
        } else if (doctorsDTO.getSpecialization() == null || doctorsDTO.getSpecialization().isEmpty()) {
            return "Specialization cannot be empty";
        } else {
            doctorsRepository.save(modelMapping.doctors(doctorsDTO));
            return "Doctor added successfully";
        }
    }

    public String updateDoctor(DoctorsDTO doctorsDTO) {
        if (!doctorsRepository.existsById(doctorsDTO.getDoctorId())) {
            return "Doctor not found";
        } else if (doctorsDTO.getDoctorName() == null || doctorsDTO.getDoctorName().isEmpty()) {
            return "Doctor name cannot be empty";
        } else if (doctorsDTO.getFees() <= 0) {
            return "Fees must be greater than 0";
        } else if (doctorsDTO.getSpecialization() == null || doctorsDTO.getSpecialization().isEmpty()) {
            return "Specialization cannot be empty";
        } else {
            doctorsRepository.save(modelMapping.doctors(doctorsDTO));
            return "Doctor updated successfully";
        }
    }

    public String deleteDoctor(int id) {
        if (id <= 0) {
            return "Invalid doctor ID";
        } else if (!doctorsRepository.existsById(id)) {
            return "Doctor not found";
        } else {
            doctorsRepository.deleteById(id);
            return "Doctor deleted successfully";
        }
    }

}