package com.example.appointment.controller;

import com.example.appointment.model.Appointment;

import com.example.appointment.model.Doctor;

import com.example.appointment.model.Patient;
import com.example.appointment.service.AppointmentServiceImpl;
import com.example.appointment.service.DoctorServiceImpl;
import com.example.appointment.service.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {
    @Autowired
    private AppointmentServiceImpl appointmentService;
    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    PatientServiceImpl patientService;
    @PostMapping("/doctor/{doctorId}/appointments")
    public void add(@PathVariable(value = "doctorId") int doctorId, @RequestBody Appointment appointment){

        Doctor doctor=doctorService.getById(doctorId);
        appointmentService.convertDate(appointment,doctor);

    }
    //فعلا لازم ندارم
   /* @GetMapping("/getbyid/{appid}")
    public Appoinment getbyid(@PathVariable (value = "appid") int appId){
        return appoinmentService.getById(appId);
    }
*/
    @PutMapping("/patient/{patientId}/appointments")
    public void reserveAppointment(@PathVariable(value = "patientId") int patientId, @RequestParam(value = "appointmentId") int appointmentId){

        Patient patient=patientService.getById(patientId);
        Appointment appointment = appointmentService.getById(appointmentId);
        appointmentService.reserveAppointment(appointment,patient);

    }

    @PutMapping("/patient/{patientName}/{patientPhone}/appointments")
    public void reserveAppointmentByNameAndPhone(@PathVariable(value = "patientName") String patientName, @PathVariable(value = "patientPhone") Long patientPhone, @RequestParam(value = "appointmentId") int appointmentId){

        Patient patient=patientService.getByNameAndPhone(patientName,patientPhone);
        Appointment appointment = appointmentService.getById(appointmentId);
        appointmentService.reserveAppointment(appointment,patient);
    }

    @GetMapping("/doctor/{doctorId}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorId(@PathVariable(value = "doctorId") int doctorId) {

        List<Appointment> appointmentList = appointmentService.findByDoctorId(doctorId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }
    @DeleteMapping("/doctor/{doctorId}/deleteappointments")
    public ResponseEntity deleteAppointmentsById(@PathVariable(value = "doctorId") int doctorId , @RequestParam(value = "appointmentId") int appointmentId) {

        List<Appointment> appointmentList = appointmentService.findByDoctorId(doctorId);
        if (appointmentList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
      Appointment appointment = appointmentService.getById(appointmentId);
        if (appointment.getIs_taken()==true){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        appointmentService.deleteById(appointmentId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/availableappointments/{page}/{size}")
    public ResponseEntity<List<Appointment>> getAvailableAppointments(@PathVariable int page, @PathVariable int size) {


        Page<Appointment> appointmentPage=appointmentService.findAllAvailableAppointments(page,size);
        List<Appointment> appointmentList =appointmentPage.getContent();
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientPhone}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientPhone(@PathVariable(value = "patientPhone") Long patientPhone) {

        Patient patient=patientService.getByPhone(patientPhone);
        List<Appointment> appointmentList = appointmentService.findByPatientId(patient.getPatientId());
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

}
