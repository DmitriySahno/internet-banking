package com.sahd.Internetbanking.exception;

public class OperationNotFoundException extends RuntimeException{
    public OperationNotFoundException(String message) {
        super(message);
    }
}
