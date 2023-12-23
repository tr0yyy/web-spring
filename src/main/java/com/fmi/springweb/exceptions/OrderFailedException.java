package com.fmi.springweb.exceptions;

public class OrderFailedException extends Exception{
    public OrderFailedException (String errorMessage) {
        super(errorMessage);
    }
}
