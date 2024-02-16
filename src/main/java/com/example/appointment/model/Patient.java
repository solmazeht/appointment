package com.example.appointment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "p_seq")
    @SequenceGenerator(sequenceName = "p_seq",allocationSize = 1,name = "p_seq")
    private int patientId;
    private String patient_name;
    private String patient_lastName;
    private Long patient_number;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "patient")
    @JsonIgnore
    private List<Appointment> appointments;

    public Patient(String patient_name, String patient_lastName, Long patient_number, List<Appointment> appointments) {
        this.patient_name = patient_name;
        this.patient_lastName = patient_lastName;
        this.patient_number = patient_number;
        this.appointments = appointments;
    }

    public Patient() {
        super();
    }

    public int getPatientId() {
        return patientId;
    }

    public Patient setPatientId(int patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public Patient setPatient_name(String patient_name) {
        this.patient_name = patient_name;
        return this;
    }

    public String getPatient_lastName() {
        return patient_lastName;
    }

    public Patient setPatient_lastName(String patient_lastName) {
        this.patient_lastName = patient_lastName;
        return this;
    }

    public Long getPatient_number() {
        return patient_number;
    }

    public Patient setPatient_number(Long patient_number) {
        this.patient_number = patient_number;
        return this;
    }

    public List<Appointment> getAppoinments() {
        return appointments;
    }

    public Patient setAppoinments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }
}
