package com.polyclinic.polyclinicapp;

import com.polyclinic.polyclinicapp.DTO.PatientsDTO;
import com.polyclinic.polyclinicapp.entity.Patients;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapping {

    @Autowired
    ModelMapper modelMapper;

    public PatientsDTO patientsDTO(Patients patients) {
        return modelMapper.map(patients, PatientsDTO.class);
    }

    public  List<PatientsDTO> patientsDTOList(List<Patients> patientsList) {
        return patientsList.stream()
                .map(this::patientsDTO)
                .collect(Collectors.toList());
    }

    public Patients patients(PatientsDTO patientsDTO) {
        return modelMapper.map(patientsDTO, Patients.class);
    }

    public  List<Patients> patientsList(List<PatientsDTO> patientsDTOList) {
        return patientsDTOList.stream()
                .map(this::patients)
                .collect(Collectors.toList());
    }

}
