package ru.hofftech.parcellogistic.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Slf4j
public class TxtReader {

    @SneakyThrows
    public List<String> readAllLines(String filePath) {
        try {
            URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource(filePath)).toURI();

            return Files.readAllLines(
                    new File(uri).toPath()
            );
        } catch (Exception e) {
            throw new IOException("Error while reading file " + filePath + " : " + e.getMessage());
        }
    }
}
