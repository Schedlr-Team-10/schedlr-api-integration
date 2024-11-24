package com.api.schedlr.schedlr_api_integration.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class JsonConverter implements AttributeConverter<List<String>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> tags) {
        try {
            return objectMapper.writeValueAsString(tags);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert tags to JSON string", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON string to tags list", e);
        }
    }
}
