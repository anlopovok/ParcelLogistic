package ru.hofftech.parcellogistic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.model.UnloadTrucksResult;
import ru.hofftech.parcellogistic.model.json.TruckJsonNode;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;
import ru.hofftech.parcellogistic.util.TrucksJsonFileParser;

import java.util.List;

/**
 * Service class responsible for unloading trucks from a specified file and retrieving the associated parcels.
 * It processes a file containing truck data and maps each parcel to its corresponding entity in the system.
 */
@Slf4j
@AllArgsConstructor
public final class UnloadTrucksService {

    private final TrucksJsonFileParser fileParser;

    private final ParcelService parcelService;

    /**
     * Unloads trucks from a specified file, retrieves the corresponding parcels, and returns the result.
     *
     * @param filePath the path to the JSON file containing truck data
     * @return UnloadTrucksResult the result containing the list of parcels unloaded from the trucks
     */
    public UnloadTrucksResult unloadTrucksFromFile(String filePath) {
        List<TruckJsonNode> truckJsonNodes = fileParser.parseFromFile(filePath);

        log.debug("Start import trucks");
        List<ParcelEntity> parcels = truckJsonNodes.stream()
                .flatMap(truckJsonNode -> truckJsonNode.getParcels().stream())
                .map(jsonParcel -> parcelService.getParcelByName(jsonParcel.getName()))
                .toList();

        UnloadTrucksResult unloadTrucksResult = new UnloadTrucksResult(parcels);
        log.debug("End import trucks");

        return unloadTrucksResult;
    }
}
