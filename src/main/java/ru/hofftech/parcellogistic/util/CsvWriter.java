package ru.hofftech.parcellogistic.util;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.exception.ErrorWhileWritingFileException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for writing CSV data to a file.
 *
 * This class provides functionality to save a list of columns (represented as a list of lists of strings)
 * into a CSV file, with a specified delimiter separating the columns in each row.
 */
@Slf4j
public class CsvWriter {

    /**
     * Saves the given columns to a CSV file.
     *
     * This method will write the provided list of columns into a file at the specified path,
     * using the specified delimiter to separate the values in each row.
     *
     * @param filePath the path of the CSV file to save the content to
     * @param columns a list of columns, where each column is represented as a list of strings
     * @param delimiter the delimiter to use for separating values in each column
     * @throws IllegalArgumentException if the delimiter is null or empty
     * @throws ErrorWhileWritingFileException if there is an error while writing the file
     */
    public void save(String filePath, List<List<String>> columns, String delimiter) {
        if (delimiter == null || delimiter.isEmpty()) {
            throw new IllegalArgumentException("Delimiter is null or empty");
        }

        try {
            Path path = Paths.get(ClassLoader.getSystemResource("").toURI()).resolve(filePath);
            Files.createDirectories(path.getParent());

            String content = columns.stream()
                    .map(line -> String.join(delimiter, line))
                    .collect(Collectors.joining(System.lineSeparator()));

            Files.writeString(path, content);
            log.debug("Content saved to file {}", filePath);
        } catch (Exception e) {
            throw new ErrorWhileWritingFileException(filePath);
        }
    }
}
