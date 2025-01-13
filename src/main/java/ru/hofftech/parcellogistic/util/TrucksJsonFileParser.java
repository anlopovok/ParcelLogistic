package ru.hofftech.parcellogistic.util;

import lombok.SneakyThrows;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.model.dto.TrucksJsonNodeDto;

import java.io.IOException;

@Slf4j
public class TrucksJsonFileParser {

    private static final String FILE_PATH_PATTERN = ".+\\.json";

    private final JsonReader jsonReader;

    public TrucksJsonFileParser(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    @SneakyThrows
    public TrucksJsonNodeDto parseFromFile(String filePath) {
        if (!filePath.matches(FILE_PATH_PATTERN)) {
            throw new IllegalArgumentException("File extension should be json");
        }

        try {
            log.debug("Start parsing trucks from file {}", filePath);
            TrucksJsonNodeDto trucksJsonNodeDto = jsonReader.readValue(filePath, TrucksJsonNodeDto.class);
            log.debug("Parsing trucks completed");

            return trucksJsonNodeDto;
        } catch (Exception e) {
            throw new IOException("Error while parsing file " + filePath);
        }
    }
}
