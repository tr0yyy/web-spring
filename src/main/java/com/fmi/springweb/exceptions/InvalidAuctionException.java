package com.fmi.springweb.exceptions;

public class InvalidAuctionException extends Exception {
    public InvalidAuctionException (String errorMessage) {
        super(errorMessage);
    }
}
