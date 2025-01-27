package ru.hofftech.parcellogistic.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.exception.ErrorWhileWritingFileException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for writing JSON data to a file.
 *
 * This class provides functionality to serialize an object into a formatted JSON string
 * and write it to a file in the resources folder. If the file already exists, it is overwritten.
 * If the file does not exist, it will be created.
 *
 * Uses the Jackson ObjectMapper to convert Java objects to JSON and save the content to a file.
 */
@Slf4j
public class JsonWriter {

    /**
     * Saves an object as a formatted JSON file.
     *
     * This method serializes the given object into JSON format using Jackson's ObjectMapper
     * and writes the result to a file at the specified path. The file is created if it doesn't exist,
     * and overwritten if it does.
     *
     * @param filePath the relative path where the JSON file will be saved
     * @param value the object to be serialized and saved as JSON
     * @throws ErrorWhileWritingFileException if an error occurs while writing the file
     */
    public void save(String filePath, Object value) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource("").toURI()).resolve(filePath);
            Files.createDirectories(path.getParent());
            File file = Files.exists(path) ? path.toFile() : Files.createFile(path).toFile();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(
                    file,
                    value
            );
            log.debug("Content saved to file {}", filePath);
        } catch (Exception e) {
            throw new ErrorWhileWritingFileException(filePath);
        }
    }
}
