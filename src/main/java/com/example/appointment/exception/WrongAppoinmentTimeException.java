package com.example.appointment.exception;


public class WrongAppoinmentTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WrongAppoinmentTimeException(String msg) {
        super(msg);
    }
}
