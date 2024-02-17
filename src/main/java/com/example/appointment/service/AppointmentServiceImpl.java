package com.example.appointment.service;

import com.example.appointment.exception.AppointmentNotAvailable;
import com.example.appointment.exception.WrongAppointmentTimeException;
import com.example.appointment.model.Appointment;
import com.example.appointment.model.Doctor;
import com.example.appointment.model.Patient;
import com.example.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public void save(Appointment appointment) {
      appointmentRepository.save(appointment);
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
                appointment1.setAppId(lastAppointmentId()+1);
                appointment1.setStart_time(String.valueOf(startTime));
                endTime = startTime.plus(Duration.ofMinutes(30));
                appointment1.setEnd_time(String.valueOf(endTime));
                appointment1.setIs_taken(appointment.getIs_taken());
                appointment1.setDoctor(doctor);
                appointment1.setDay(appointment.getDay());
                startTime = startTime.plus(Duration.ofMinutes(30));
                appointmentRepository.save(appointment1);
            }
        }else throw new WrongAppointmentTimeException("Start Time must be Before End Time");

    }

    @Override
    public List<Appointment> findByDoctorId(int doctor_id) {

        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctor_id);

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
    public Appointment reserveAppointment(Appointment appointment, Patient patient) {
        if (appointment.getIs_taken()==true){
            throw new AppointmentNotAvailable("appointment reserved by someone else");
        }
        appointment.setPatient(patient);
        appointment.setIs_taken(true);
        return appointmentRepository.save(appointment);
    }

  @Override
    public Appointment getById(int appId) {

        Optional<Appointment> optAppointment = appointmentRepository.findById(appId);

        if (!optAppointment.isPresent()) {

            throw new NotFoundException("not found");
        }
        return optAppointment.get();
    }

    @Override
    @Transactional
    public void deleteById(int appId) {

        appointmentRepository.deleteById(appId);
    }

    @Override
    public Page<Appointment> findAllAvailableAppointments(int page,int size) {

       // Page<Appointment> carPages = appointmentRepository.findAll(pageRequest);
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Appointment> availableAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getIs_taken() == false) {
                availableAppointments.add(appointment);
            }
        }


        Pageable pageRequest = PageRequest.of(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), availableAppointments.size());

        List<Appointment> pageContent = availableAppointments.subList(start, end);
        return new PageImpl<Appointment>(pageContent, pageRequest, availableAppointments.size());

    }

    @Override
    public List<Appointment> findByPatientId(int patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public int lastAppointmentId() {
        List<Appointment> appointments=appointmentRepository.findLastAppointment();
        if (appointments.size()==0){
            return 0;
        }
        Appointment appointment=appointments.get(appointments.size()-1);
        return appointment.getAppId();
    }
}
