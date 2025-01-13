package ru.hofftech.parcellogistic.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class JsonWriter {

    @SneakyThrows
    public void save(String directory, String fileName, Object value) {
        try {
            String workingDir = System.getProperty("user.dir");

            Path dirPath = Paths.get(workingDir, directory);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path filePath = dirPath.resolve(fileName);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(
                    Files.createFile(filePath).toFile(),
                    value
            );
            log.debug("Content saved to file {}", filePath);
        } catch (Exception e) {
            throw new IOException("Error while writing file " + fileName + " : " + e.getMessage());
        }
    }
}
