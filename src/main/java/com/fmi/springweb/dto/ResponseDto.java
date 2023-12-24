package com.fmi.springweb.dto;

public class ResponseDto {
    public boolean success;
    public Object result;

    public ResponseDto (boolean success, Object result) {
        this.success = success;
        this.result = result;
    }
}
