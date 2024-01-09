package com.fmi.springweb.component;

import com.fmi.springweb.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RequestHandler {
    public static Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    @FunctionalInterface
    public interface Callback<T> {
        ResponseEntity<ResponseDto<T>> onCallBack() throws Exception;
    }

    public static <T> ResponseEntity<ResponseDto<T>> handleRequest(Callback<T> callback,
                                                                   Class<? extends Exception> exceptionType) {
        try {
            return callback.onCallBack();
        } catch (Exception e) {
            // if exception type is expected one,
            // we will return 200 OK with success false and specific error message
            if (exceptionType.isInstance(e)) {
                return ResponseEntity.ok(
                        new ResponseDto<>(e.getMessage()));
            }
            // if exception type is not expected,
            // we will return server error
            logger.error(e.getMessage());
            logger.error(Arrays.toString(e.getStackTrace()));
            return ResponseEntity.internalServerError().body(
                    new ResponseDto<>("Internal server error"));
        }
    }
}
