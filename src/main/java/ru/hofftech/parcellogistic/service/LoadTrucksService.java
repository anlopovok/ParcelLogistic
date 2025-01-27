package ru.hofftech.parcellogistic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.exception.IllegalCommandArgumentException;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.Size;

import java.util.List;

/**
 * Service for loading parcels into trucks using different placement algorithms.
 * This service determines the appropriate algorithm for arranging parcels
 * into trucks and ensures that the input data is valid before processing.
 */
@Slf4j
@AllArgsConstructor
public class LoadTrucksService {

    private final PlacementAlgorithmServiceFactory placementAlgorithmServiceFactory;

    /**
     * Loads parcels into trucks based on the given placement algorithm.
     *
     * @param parcels             List of parcels to be loaded.
     * @param placementAlgorithm  The algorithm used for truck loading.
     * @param trucksSizes         List of available truck sizes.
     * @return {@link LoadTrucksResult} containing the loaded trucks and their parcels.
     * @throws IllegalCommandArgumentException If the parcel list is empty or no trucks are provided.
     */
    public LoadTrucksResult loadTrucks(
            List<Parcel> parcels,
            PlacementAlgorithm placementAlgorithm,
            List<Size> trucksSizes
    ) {
        if (parcels == null || parcels.isEmpty()) {
            throw new IllegalCommandArgumentException("Empty input parcels");
        }
        if (trucksSizes.isEmpty()) {
            throw new IllegalCommandArgumentException("Trucks count must be more than 0");
        }

        log.debug("Start load parcels");
        LoadTrucksResult loadTrucksResult = placementAlgorithmServiceFactory
                .getPlacementAlgorithmService(placementAlgorithm)
                .placeParcelsToTrucks(parcels, trucksSizes);
        log.debug("End load parcels");

        return loadTrucksResult;
    }
}
