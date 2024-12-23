package ru.hofftech.parcellogistic.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
public class TxtReader {

    @SneakyThrows
    public List<String> readAllLines(String filePath) {
        try {
            return Files.readAllLines(
                    new File(getClass().getClassLoader().getResource(filePath).toURI()).toPath()
            );
        } catch (Exception e) {
            log.error("File {} not found", filePath);
            throw new IOException("Invalid file path");
        }
    }
}
