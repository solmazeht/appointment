package com.example.appointment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "d_seq")
    @SequenceGenerator(sequenceName = "d_seq",allocationSize = 1,name = "d_seq",initialValue = 1)
    private int doctorId;
    private String doctor_name;
    private String doctor_lastName;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "doctor")
    @JsonIgnore
    private List<Appointment> appointments;

    public Doctor() {
        super();
    }

    public Doctor(String doctor_name, String doctor_lastName, List<Appointment> appointments) {

        this.doctor_name = doctor_name;
        this.doctor_lastName = doctor_lastName;
        this.appointments = appointments;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public Doctor setDoctorId(int doctor_id) {
        this.doctorId = doctor_id;
        return this;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public Doctor setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
        return this;
    }

    public String getDoctor_lastName() {
        return doctor_lastName;
    }

    public Doctor setDoctor_lastName(String doctor_lastName) {
        this.doctor_lastName = doctor_lastName;
        return this;
    }

    public List getAppoinments() {
        return appointments;
    }

    public Doctor setAppoinments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }
}
