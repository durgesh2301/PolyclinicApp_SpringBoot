package com.polyclinic.polyclinicapp.Controllers;

import com.polyclinic.polyclinicapp.DTO.DoctorsDTO;
import com.polyclinic.polyclinicapp.ModelMapping;
import com.polyclinic.polyclinicapp.Repositories.DoctorsRepository;
import com.polyclinic.polyclinicapp.entity.Doctors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class DoctorsController {
    @Autowired
    DoctorsRepository doctorsRepository;
    @Autowired
    ModelMapping modelMapping;

   @GetMapping("/getDoctors")
   public List<DoctorsDTO> getAllDoctors() {
       return modelMapping.doctorsDTOList(doctorsRepository.findAll()) ;
   }

   @GetMapping("/getDoctorsById")
   public ResponseEntity<DoctorsDTO> getDoctorsById(@RequestParam int id) {
       Optional<Doctors> doctors = doctorsRepository.findById(id);
       if (doctors.isPresent()) {
           DoctorsDTO doctorsDTO = modelMapping.doctorsDTO(doctors.get());
           return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }

   @GetMapping("/getDoctorsByName")
   public ResponseEntity<List<DoctorsDTO>> getDoctorsByName(@RequestParam String name) {
       List<DoctorsDTO> doctors = modelMapping.doctorsDTOList(doctorsRepository.findAll());
       List<DoctorsDTO> filteredDoctors = doctors.stream()
               .filter(doctor -> doctor.getDoctorName().toLowerCase().contains(name.toLowerCase()))
               .toList();
       if (!filteredDoctors.isEmpty()) {
           return new ResponseEntity<>(filteredDoctors, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }

    @PostMapping("/addDoctor")
    public String addDoctor(@RequestBody DoctorsDTO doctorsDTO) {
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

    @PutMapping("/updateDoctor")
    public String updateDoctor(@RequestBody DoctorsDTO doctorsDTO) {
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

    @DeleteMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam int id) {
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