package com.fmi.springweb.exceptions;

public class InvalidBidException extends Exception {
    public InvalidBidException (String errorMessage) {
        super(errorMessage);
    }
}
