package com.example.appointment.model;


import javax.persistence.*;

@Entity
@Table
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "ap_seq")
    @SequenceGenerator(sequenceName = "ap_seq",allocationSize = 1,name = "ap_seq",initialValue = 1)
    private int appId;

   @Column(name = "day", columnDefinition = "Date")
    private String day;
    @Column(name = "start_time", columnDefinition = "TIME")
    private String start_time;

   @Column(name = "end_time", columnDefinition = "TIME")
    private String end_time;

    private Boolean is_taken;

    @ManyToOne
    @JoinColumn(name = "doctorId",referencedColumnName ="doctorId")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientId",referencedColumnName ="patientId")
    private Patient patient;

    public Appointment() {
        super();
    }

    public Appointment(String day, String start_time, String end_time, Boolean is_taken, Doctor doctor, Patient patient) {
        this.day = day;
        this.start_time = start_time;
        this.end_time = end_time;
        this.is_taken = is_taken;
        this.doctor = doctor;
        this.patient=patient;
    }

    public int getAppId() {
        return appId;
    }

    public Appointment setAppId(int app_id) {
        this.appId = app_id;
        return this;
    }

    public Patient getPatient() {
        return patient;
    }

    public Appointment setPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public String getDay() {
        return day;
    }

    public Appointment setDay(String day) {
        this.day = day;
        return this;
    }

    public String getStart_time() {
        return start_time;
    }

    public Appointment setStart_time(String start_time) {
        this.start_time = start_time;
        return this;
    }

    public String getEnd_time() {
        return end_time;
    }

    public Appointment setEnd_time(String end_time) {
        this.end_time = end_time;
        return this;
    }

    public Boolean getIs_taken() {
        return is_taken;
    }

    public Appointment setIs_taken(Boolean is_taken) {
        this.is_taken = is_taken;
        return this;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Appointment setDoctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }


}
