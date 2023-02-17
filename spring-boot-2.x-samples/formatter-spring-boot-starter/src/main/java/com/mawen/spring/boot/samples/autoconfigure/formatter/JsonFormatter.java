package com.mawen.spring.boot.samples.autoconfigure.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/16
 */
public class JsonFormatter implements Formatter{

    private final ObjectMapper objectMapper;

    public JsonFormatter() {
        this(new ObjectMapper());
    }

    public JsonFormatter(ObjectMapper objectMapper) {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String format(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
