package com.hahrens.controller.implementation.registration.exception;

public class UserAlreadyExistsException extends RuntimeException{


    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
