package com.fmi.springweb.dto;

public class ResponseDto<T> {
    public boolean success;
    public T result;
    public String error;

    public ResponseDto (boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public ResponseDto (String error) {
        this.success = false;
        this.error = error;
    }
}
