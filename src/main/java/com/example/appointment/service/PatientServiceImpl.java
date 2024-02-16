package com.example.appointment.service;

import com.example.appointment.exception.MissingInput;
import com.example.appointment.model.Patient;
import com.example.appointment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{
    @Autowired
    PatientRepository patientRepository;

    @Override
    public Patient savePatient(Patient patient) {
        Patient savedPatient=patientRepository.save(patient);
        return savedPatient;
    }

    @Override
    public Patient getById(int patientId) {
        Optional<Patient> optionalPatient=patientRepository.findById(patientId);
        if (!optionalPatient.isPresent()){
            throw new NotFoundException("not found patient");
        }
        return optionalPatient.get();
    }

    @Override
    public Patient getByNameAndPhone(String name, Long phone) {
        if (name=="" || phone==null){
            throw new MissingInput("missing name or phone");
        }
        return patientRepository.findByNameAndPhone(name, phone);
    }

    @Override
    public Patient getByPhone(Long phone) {
        return patientRepository.findByPhone(phone);
    }
}
