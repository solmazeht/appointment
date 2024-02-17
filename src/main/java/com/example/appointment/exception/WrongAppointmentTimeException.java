package com.example.appointment.exception;


public class WrongAppointmentTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WrongAppointmentTimeException(String msg) {
        super(msg);
    }
}
