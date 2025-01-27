package ru.hofftech.parcellogistic.util;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.exception.ErrorWhileParsingFileException;
import ru.hofftech.parcellogistic.exception.InvalidFileExtension;
import ru.hofftech.parcellogistic.model.json.TruckJsonNode;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for parsing truck data from a JSON file.
 *
 * This class provides functionality to read truck data from a JSON file and
 * convert it into a list of {@link TruckJsonNode} objects.
 */
@Slf4j
@AllArgsConstructor
public class TrucksJsonFileParser {

    private static final String FILE_EXTENSION_PATTERN = ".+\\.json";

    private static final String FILE_EXTENSION = "json";

    private final JsonReader jsonReader;

    /**
     * Parses truck data from the specified JSON file.
     *
     * This method reads the JSON file and converts it into a list of {@link TruckJsonNode} objects.
     * Only files with the `.json` extension are supported.
     *
     * @param filePath the path to the JSON file containing truck data
     * @return List<TruckJsonNode> a list of {@link TruckJsonNode} objects parsed from the file
     * @throws InvalidFileExtension if the file does not have a `.json` extension
     * @throws ErrorWhileParsingFileException if an error occurs while reading the file or processing its contents
     */
    public List<TruckJsonNode> parseFromFile(String filePath) {
        if (!filePath.matches(FILE_EXTENSION_PATTERN)) {
            throw new InvalidFileExtension(filePath, FILE_EXTENSION);
        }

        try {
            log.debug("Start parsing trucks from file {}", filePath);
            List<TruckJsonNode> truckJsonNodes = Arrays.asList(
                    jsonReader.readValue(filePath, TruckJsonNode[].class)
            );
            log.debug("Parsing trucks completed");

            return truckJsonNodes;
        } catch (Exception e) {
            throw new ErrorWhileParsingFileException(filePath);
        }
    }
}
