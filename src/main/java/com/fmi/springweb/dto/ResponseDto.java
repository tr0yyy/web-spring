package com.fmi.springweb.dto;

public class ResponseDto {
    public boolean success;
    public String message;

    public ResponseDto (boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
