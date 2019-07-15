package io.fourfinanceit.homework.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JsonDeserialize
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(jsonparser.getText(), formatter);
    }
}
