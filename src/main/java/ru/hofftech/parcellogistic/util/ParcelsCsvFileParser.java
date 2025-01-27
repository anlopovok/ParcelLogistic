package ru.hofftech.parcellogistic.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.exception.ErrorWhileParsingFileException;
import ru.hofftech.parcellogistic.exception.InvalidFileExtension;

import java.util.List;

/**
 * Utility class for parsing parcel data from a CSV file.
 *
 * This class handles the parsing of parcel names from a CSV file. The file is expected to be in
 * CSV format with a semicolon delimiter. The first column in the CSV should contain the parcel names.
 * The file is validated for the correct extension (.csv) before parsing.
 */
@Slf4j
@AllArgsConstructor
public class ParcelsCsvFileParser {

    private static final String FILE_EXTENSION_PATTERN = ".+\\.csv";

    private static final String FILE_EXTENSION = "csv";

    private static final String DELIMITER = ";";

    private final CsvReader csvReader;

    /**
     * Parses the parcel names from a CSV file.
     *
     * This method reads the CSV file from the specified path, validates its extension,
     * and extracts parcel names from the first column. It returns a list of parcel names as strings.
     *
     * @param filePath the path to the CSV file
     * @return a list of parcel names
     * @throws InvalidFileExtension if the file extension is not ".csv"
     * @throws ErrorWhileParsingFileException if an error occurs while reading or parsing the file
     */
    public List<String> parseFromFile(String filePath) {
        validateFileExtension(filePath);

        try {
            log.debug("Start parsing parcels from file {}", filePath);

            List<String> parcels = csvReader.readColumns(filePath, DELIMITER).stream()
                    .filter(row -> !row.isEmpty())
                    .map(row -> row.getFirst().replace("\"", ""))
                    .toList();

            log.debug("Parsed parcels count: {}", parcels.size());

            return parcels;
        } catch (Exception e) {
            throw new ErrorWhileParsingFileException(filePath);
        }
    }

    private void validateFileExtension(String filePath) {
        if (!filePath.matches(FILE_EXTENSION_PATTERN)) {
            throw new InvalidFileExtension(filePath, FILE_EXTENSION);
        }
    }
}
