package com.example.appointment.exception;

public class MissingInput extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public MissingInput(String msg) {
        super(msg);
    }
}
