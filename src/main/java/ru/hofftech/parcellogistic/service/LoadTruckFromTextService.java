package ru.hofftech.parcellogistic.service;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.Size;

import java.util.List;

/**
 * Service for loading trucks from a list of parcel names provided as text input.
 * This service retrieves {@link Parcel} objects from the provided names and
 * utilizes {@link LoadTrucksService} to load them into trucks based on a
 * specified placement algorithm.
 */
@AllArgsConstructor
public class LoadTruckFromTextService {

    private final LoadTrucksService loadTrucksService;

    private final ParcelService parcelService;

    /**
     * Loads trucks by retrieving parcel details from the provided names and applying the specified placement algorithm.
     *
     * @param names               List of parcel names.
     * @param placementAlgorithm  Algorithm used to place parcels into trucks.
     * @param trucksSizes         List of available truck sizes.
     * @return {@link LoadTrucksResult} containing the loaded trucks and their contents.
     */
    public LoadTrucksResult loadTrucks(
            List<String> names,
            PlacementAlgorithm placementAlgorithm,
            List<Size> trucksSizes
    ) {
        List<Parcel> parcels = names.stream().map(
                name -> Parcel.fromEntity(parcelService.getParcelByName(name))
        ).toList();

        return loadTrucksService.loadTrucks(parcels, placementAlgorithm, trucksSizes);
    }
}
