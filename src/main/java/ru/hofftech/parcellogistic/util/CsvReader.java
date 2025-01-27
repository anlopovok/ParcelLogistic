package ru.hofftech.parcellogistic.util;

import ru.hofftech.parcellogistic.exception.ErrorWhileReadingFileException;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for reading CSV files and extracting columns.
 *
 * This class is responsible for reading a CSV file from the resources and splitting
 * the content into columns based on the specified delimiter.
 */
public class CsvReader {

    /**
     * Reads the columns from a CSV file.
     *
     * This method reads the file from the provided file path, splits each row by the
     * specified delimiter, and returns a list of columns as a list of lists of strings.
     *
     * @param filePath the relative path of the CSV file in the resources folder
     * @param delimiter the delimiter used to split the columns in the CSV file
     * @return a list of columns, where each column is represented as a list of strings
     * @throws ErrorWhileReadingFileException if there is an error reading the file or parsing the content
     */
    public List<List<String>> readColumns(String filePath, String delimiter) {
        try {
            URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource(filePath)).toURI();

            List<String> rows = Files.readAllLines(
                    new File(uri).toPath()
            );

            return rows.stream()
                    .map(row -> Arrays.stream(row.split(delimiter)).toList())
                    .toList();
        } catch (Exception e) {
            throw new ErrorWhileReadingFileException(filePath);
        }
    }
}
