package com.fmi.springweb.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.springweb.dto.ResponseDto;

public class Utils {
    public static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ResponseDto<T> convertStringToResponseDto(Class<T> resultType, String object) throws JsonProcessingException {
        TypeReference<ResponseDto<T>> typeReference = new TypeReference<>() {};
        return new ObjectMapper().readValue(object, typeReference);
    }
}
