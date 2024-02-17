package com.example.appointment.service;

import com.example.appointment.model.Appointment;
import com.example.appointment.model.Doctor;
import com.example.appointment.model.Patient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppointmentService {

    void save(Appointment appointment);
    void convertDate(Appointment appointment, Doctor doctor);
    List<Appointment> findByDoctorId(int doctorId);
    Appointment reserveAppointment(Appointment appointment, Patient patient);
    Appointment getById(int appId);
    void deleteById(int appId);
   // List<Appointment> findAllAvailableAppointments();
    Page<Appointment> findAllAvailableAppointments(int page,int size);
    List<Appointment> findByPatientId(int patientId);
    int lastAppointmentId();

}
