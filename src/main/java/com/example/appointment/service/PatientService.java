package com.example.appointment.service;

import com.example.appointment.model.Patient;

public interface PatientService {
    Patient savePatient(Patient patient);
    Patient getById(int patientId);
    Patient getByNameAndPhone(String name,Long phone);
    Patient getByPhone(Long phone);
}
