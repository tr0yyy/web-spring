package com.fmi.springweb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class ResponseDto<T> {
    @NotNull
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

    public ResponseDto () {}

    @JsonCreator
    public ResponseDto(@JsonProperty("success") boolean success, @JsonProperty("result") T result, @JsonProperty("error") String error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }
}
