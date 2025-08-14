package com.polyclinic.polyclinicapp.Controllers;

import com.polyclinic.polyclinicapp.Repositories.PatientsRepository;
import com.polyclinic.polyclinicapp.Repositories.PersonRepository;
import com.polyclinic.polyclinicapp.entity.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonRepository repository;

    @GetMapping("/getPerson")
    public long getPerson(){
        return repository.count();
    }

}
