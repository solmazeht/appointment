package com.example.appointment.service;

import com.example.appointment.model.Doctor;
import com.example.appointment.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;
    @Override
    public Doctor saveDoctor(Doctor doctor) {

     Doctor savedDoctor= doctorRepository.save(doctor);
     return savedDoctor;
    }

    @Override
    public Doctor getById(int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

        if (!optionalDoctor.isPresent()) {

            throw new NotFoundException("not found");
        }


        return optionalDoctor.get();

    }
}
