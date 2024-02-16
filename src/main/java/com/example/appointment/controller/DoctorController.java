package com.example.appointment.controller;

import com.example.appointment.model.Doctor;
import com.example.appointment.service.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DoctorController {
    @Autowired
    private DoctorServiceImpl doctorService;
    @PostMapping("/doctor")
    public void add(@RequestBody Doctor doctor){

        doctorService.saveDoctor(doctor);
    }
    @GetMapping("/getbyid")
    public Doctor getById(@RequestParam int doctorId){

        return doctorService.getById(doctorId);
    }


}
