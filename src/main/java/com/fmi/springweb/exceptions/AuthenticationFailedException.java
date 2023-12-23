package com.fmi.springweb.exceptions;

public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String errorMessage) {
        super(errorMessage);
    }
}
