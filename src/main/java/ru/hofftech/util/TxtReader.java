package ru.hofftech.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TxtReader {

    public List<String> readAllLines(String filePath) {
        try {
            return Files.readAllLines(
                    new File(getClass().getClassLoader().getResource(filePath).toURI()).toPath()
            );
        } catch (Exception e) {
            log.error("File {} not found", filePath);
            return Collections.emptyList();
        }
    }
}
