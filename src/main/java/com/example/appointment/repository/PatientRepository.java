package com.example.appointment.repository;


import com.example.appointment.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {
    @Query("select pa from Patient pa where pa.patient_lastName = ?1 and pa.patient_number= ?2")
    Patient findByNameAndPhone(String name,Long phone);

    @Query("select pa from Patient pa where pa.patient_number= ?1")
    Patient findByPhone(Long phone);

}
