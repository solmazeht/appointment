package com.example.appointment.service;

import com.example.appointment.model.Doctor;


public interface DoctorService {

    Doctor saveDoctor(Doctor doctor);
    Doctor getById(int id);
}
