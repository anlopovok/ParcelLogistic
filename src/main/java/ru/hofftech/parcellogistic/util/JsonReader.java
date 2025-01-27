package ru.hofftech.parcellogistic.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hofftech.parcellogistic.exception.ErrorWhileReadingFileException;

import java.io.File;
import java.net.URI;
import java.util.Objects;

/**
 * Utility class for reading JSON data from a file.
 *
 * This class provides functionality to read JSON data from a file located in the resources folder,
 * and deserialize it into an object of the specified type using the Jackson ObjectMapper.
 */
public class JsonReader {

    /**
     * Reads JSON data from a file and converts it into an object of the specified type.
     *
     * This method uses the Jackson ObjectMapper to read a JSON file from the resources folder,
     * and then deserialize it into an object of the provided class type.
     *
     * @param filePath the path to the JSON file in the resources folder
     * @param mapperClass the class type to deserialize the JSON into
     * @param <T> the type of object to return
     * @return the deserialized object of the specified type
     * @throws ErrorWhileReadingFileException if an error occurs while reading the file or deserializing it
     */
    public <T> T readValue(String filePath, Class<T> mapperClass) {
        try {
            URI uri = Objects.requireNonNull(getClass().getClassLoader().getResource(filePath)).toURI();
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(uri);

            return mapper.readValue(
                    file,
                    mapperClass
            );
        } catch (Exception e) {
            throw new ErrorWhileReadingFileException(filePath);
        }
    }
}
