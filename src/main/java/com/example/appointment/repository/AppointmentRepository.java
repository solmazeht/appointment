package com.example.appointment.repository;

import com.example.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    @Query("select ap from Appointment ap where ap.doctor.doctorId = ?1")
   List<Appointment> findByDoctorId(int doctorId);
    @Query("select ap from Appointment ap where ap.patient.patientId = ?1")
    List<Appointment> findByPatientId(int patientId);
    @Query("select ap from Appointment ap order by ap.appId asc ")
    List<Appointment> findLastAppointment();

}
