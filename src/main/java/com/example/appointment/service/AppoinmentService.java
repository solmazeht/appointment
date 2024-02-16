package com.example.appointment.service;

import com.example.appointment.model.Appointment;
import com.example.appointment.model.Doctor;
import com.example.appointment.model.Patient;

import java.util.List;

public interface AppoinmentService {

    void save(Appointment appointment);
    void convertDate(Appointment appointment, Doctor doctor);
    List<Appointment> findByDoctorId(int doctorId);
    Appointment reserveAppoinment(Appointment appointment, Patient patient);
    Appointment getById(int appId);
    void deleteById(int appId);
    List<Appointment> findAllAvaialableAppoinments();
    List<Appointment> findByPatientId(int patientId);

}
