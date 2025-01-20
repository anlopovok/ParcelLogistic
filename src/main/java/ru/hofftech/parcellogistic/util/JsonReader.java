package ru.hofftech.parcellogistic.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@Slf4j
public class JsonReader {

    @SneakyThrows
    public <T> T readValue(String filePath, Class<T> mapperClass) {
        try {
            URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource(filePath)).toURI();
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(
                    new File(uri),
                    mapperClass
            );
        } catch (Exception e) {
            throw new IOException("Error while reading file " + filePath + " : " + e.getMessage());
        }
    }
}
