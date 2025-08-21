package com.polyclinic.polyclinicapp.controllers;

import com.polyclinic.polyclinicapp.dto.DoctorsDTO;
import com.polyclinic.polyclinicapp.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class DoctorsController {

    @Autowired
    DoctorService doctorService;

   @GetMapping("/getDoctors")
   public List<DoctorsDTO> getAllDoctors() {
       return doctorService.getAllDoctors();
   }

   @GetMapping("/getDoctorsById")
   public ResponseEntity<DoctorsDTO> getDoctorsById(@RequestParam int id) {
       return doctorService.getDoctorsById(id);
   }

   @GetMapping("/getDoctorsByName")
   public ResponseEntity<List<DoctorsDTO>> getDoctorsByName(@RequestParam String name) {
       return doctorService.getDoctorsByName(name);
   }

    @PostMapping("/addDoctor")
    public String addDoctor(@RequestBody DoctorsDTO doctorsDTO) {
        return doctorService.addDoctor(doctorsDTO);
    }

    @PutMapping("/updateDoctor")
    public String updateDoctor(@RequestBody DoctorsDTO doctorsDTO) {
       return doctorService.updateDoctor(doctorsDTO);
    }

    @DeleteMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam int id) {
        return doctorService.deleteDoctor(id);
    }

}