package com.example.demo.configurations;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomEntityUpdater {
    private final ObjectMapper mapper;

    public CustomEntityUpdater(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T sync(Class<T> type, Object target, Object source) throws JsonMappingException {
        return type.cast(this.mapper.updateValue(target, source));
    }
}
