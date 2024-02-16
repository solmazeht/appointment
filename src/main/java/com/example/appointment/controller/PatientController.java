package com.example.appointment.controller;

import com.example.appointment.model.Patient;
import com.example.appointment.service.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {
    @Autowired
    private PatientServiceImpl patientService;
    @PostMapping("/add")
    public Patient add(@RequestBody Patient patient){
        return patientService.savePatient(patient);
    }

}
