package com.example.appointment.controller;

import com.example.appointment.model.Appointment;

import com.example.appointment.model.Doctor;

import com.example.appointment.model.Patient;
import com.example.appointment.service.AppoinmentServiceImpl;
import com.example.appointment.service.DoctorServiceImpl;
import com.example.appointment.service.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppoinmentController {
    @Autowired
    private AppoinmentServiceImpl appoinmentService;
    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    PatientServiceImpl patientService;
    @PostMapping("/doctor/{doctorId}/appoinments")
    public void add(@PathVariable(value = "doctorId") int doctorId, @RequestBody Appointment appointment){

        Doctor doctor=doctorService.getById(doctorId);
        appoinmentService.convertDate(appointment,doctor);

    }
    //فعلا لازم ندارم
   /* @GetMapping("/getbyid/{appid}")
    public Appoinment getbyid(@PathVariable (value = "appid") int appId){
        return appoinmentService.getById(appId);
    }
*/
    @PostMapping("/patient/{patientId}/appoinments")
    public void reserveAppoinment(@PathVariable(value = "patientId") int patientId, @RequestParam(value = "appoinmentId") int appoinmentId){

        Patient patient=patientService.getById(patientId);
        Appointment appointment =appoinmentService.getById(appoinmentId);
        appoinmentService.reserveAppoinment(appointment,patient);

    }

    @PostMapping("/patient/{patientName}/{patientPhone}/appoinments")
    public void reserveAppoinmentByNameAndPhone(@PathVariable(value = "patientName") String patientName, @PathVariable(value = "patientPhone") Long patientPhone, @RequestParam(value = "appoinmentId") int appoinmentId){

        Patient patient=patientService.getByNameAndPhone(patientName,patientPhone);
        Appointment appointment =appoinmentService.getById(appoinmentId);
        appoinmentService.reserveAppoinment(appointment,patient);
    }

    @GetMapping("/doctor/{doctorId}/appoinments")
    public ResponseEntity<List<Appointment>> getAppoinmentsByDoctorId(@PathVariable(value = "doctorId") int doctorId) {

        List<Appointment> appointmentList =appoinmentService.findByDoctorId(doctorId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }
    @PostMapping("/doctor/{doctorId}/deleteappoinments")
    public ResponseEntity deleteAppoinmentsById(@PathVariable(value = "doctorId") int doctorId ,@RequestParam(value = "appoinmentId") int appoinmentId) {

        List<Appointment> appointmentList =appoinmentService.findByDoctorId(doctorId);
        if (appointmentList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
      Appointment appointment =appoinmentService.getById(appoinmentId);
        if (appointment.getIs_taken()==true){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        appoinmentService.deleteById(appoinmentId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/availableappoinments")
    public ResponseEntity<List<Appointment>> getAvailableAppoinments() {

        List<Appointment> appointmentList =appoinmentService.findAllAvaialableAppoinments();
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientPhone}/appoinments")
    public ResponseEntity<List<Appointment>> getAppoinmentsByPatientPhone(@PathVariable(value = "patientPhone") Long patientPhone) {

        Patient patient=patientService.getByPhone(patientPhone);
        List<Appointment> appointmentList =appoinmentService.findByPatientId(patient.getPatientId());
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }


}
