package ru.hofftech.parcellogistic.storage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.exception.ErrorWhileParsingFileException;
import ru.hofftech.parcellogistic.model.json.ParcelEntityJsonNode;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;
import ru.hofftech.parcellogistic.util.JsonReader;

import java.util.Arrays;
import java.util.List;

/**
 * Service class for managing parcels stored in a file.
 * It handles loading parcel data from the file in JSON format.
 */
@Slf4j
@AllArgsConstructor
public class FileParcelStorage {

    private final String filePath;

    private final JsonReader jsonReader;

    /**
     * Retrieves the list of parcels from the file specified by the file path.
     *
     * @return a list of {@link ParcelEntity} objects retrieved from the file
     * @throws ErrorWhileParsingFileException if an error occurs while reading or parsing the file
     */
    public List<ParcelEntity> get() {
        try {
            log.debug("Start getting parcels from file {}", filePath);
            List<ParcelEntityJsonNode> parcelEntities = Arrays.asList(
                    jsonReader.readValue(filePath, ParcelEntityJsonNode[].class)
            );

            log.debug("Getting parcels completed");

            return parcelEntities.stream().map(jsonNode -> ParcelEntity.builder()
                            .id(jsonNode.getId())
                            .name(jsonNode.getName())
                            .content(jsonNode.getContent())
                            .build()
                    ).toList();
        } catch (Exception e) {
            throw new ErrorWhileParsingFileException(filePath);
        }
    }
}
