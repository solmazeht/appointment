package com.example.appointment.service;

import com.example.appointment.exception.AppoinmentNotAvailable;
import com.example.appointment.exception.WrongAppoinmentTimeException;
import com.example.appointment.model.Appointment;
import com.example.appointment.model.Doctor;
import com.example.appointment.model.Patient;
import com.example.appointment.repository.AppoinmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppoinmentServiceImpl implements AppoinmentService{
    @Autowired
    AppoinmentRepository appoinmentRepository;

    @Override
    @Transactional
    public void save(Appointment appointment) {
      appoinmentRepository.save(appointment);
    }

    @Override
    public void convertDate(Appointment appointment, Doctor doctor) {

        CharSequence st= appointment.getStart_time();
        CharSequence et= appointment.getEnd_time();
        LocalTime startTime= LocalTime.parse(st);
        LocalTime endTime= LocalTime.parse(et);
        Duration drt = Duration.between(startTime, endTime);
        if (startTime.isBefore(endTime)) {
            Long min = drt.toMinutes();
            int count = (int) (min / 30);
            for (int i = 0; i < count; i++) {
                Appointment appointment1 = new Appointment();
                appointment1.setAppId(i);
                appointment1.setStart_time(String.valueOf(startTime));
                endTime = startTime.plus(Duration.ofMinutes(30));
                appointment1.setEnd_time(String.valueOf(endTime));
                appointment1.setIs_taken(appointment.getIs_taken());
                appointment1.setDoctor(doctor);
                appointment1.setDay(appointment.getDay());
                startTime = startTime.plus(Duration.ofMinutes(30));
                appoinmentRepository.save(appointment1);
            }
        }else throw new WrongAppoinmentTimeException("Start Time must be Before End Time");

    }

    @Override
    public List<Appointment> findByDoctorId(int doctor_id) {

        List<Appointment> appointments =appoinmentRepository.findByDoctorId(doctor_id);

        List<Appointment> availabelAppointments =new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getIs_taken()==false){
                availabelAppointments.add(appointment);
            }
        }
        if (availabelAppointments.isEmpty()){
            return availabelAppointments;
        }

        return appointments;
    }

    @Override
    @Transactional
    public Appointment reserveAppoinment(Appointment appointment, Patient patient) {
        if (appointment.getIs_taken()==true){
            throw new AppoinmentNotAvailable("appointment reserved by someone else");
        }
        appointment.setPatient(patient);
        appointment.setIs_taken(true);
        return appoinmentRepository.save(appointment);
    }

  @Override
    public Appointment getById(int appId) {

        Optional<Appointment> optAppoinment = appoinmentRepository.findById(appId);

        if (!optAppoinment.isPresent()) {

            throw new NotFoundException("not found");
        }
        return optAppoinment.get();
    }

    @Override
    @Transactional
    public void deleteById(int appId) {

        appoinmentRepository.deleteById(appId);
    }

    @Override
    public List<Appointment> findAllAvaialableAppoinments() {
        List<Appointment> appointments = appoinmentRepository.findAll();
        List<Appointment> availableAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getIs_taken() == false) {
                availableAppointments.add(appointment);
            }
        }
            return availableAppointments;

    }

    @Override
    public List<Appointment> findByPatientId(int patientId) {
        return appoinmentRepository.findByPatientId(patientId);
    }
}
