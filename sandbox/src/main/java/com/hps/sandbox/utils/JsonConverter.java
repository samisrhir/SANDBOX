package com.hps.sandbox.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Base64;

@Converter
public class JsonConverter implements AttributeConverter<Object, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            if (attribute != null) {
                String jsonString = objectMapper.writeValueAsString(attribute);
                return Base64.getEncoder().encodeToString(jsonString.getBytes());
            }
            return null;
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to convert object to JSON string", ex);
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            if (dbData != null) {
                byte[] decodedBytes = Base64.getDecoder().decode(dbData);
                String jsonString = new String(decodedBytes);
                return objectMapper.readValue(jsonString, Object.class);
            }
            return null;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to convert JSON string to object", ex);
        }
    }
}
