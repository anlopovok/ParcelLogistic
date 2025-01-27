package ru.hofftech.parcellogistic.service;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.exception.WrongInputFilePathException;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.Size;
import ru.hofftech.parcellogistic.util.ParcelsCsvFileParser;

import java.util.List;

/**
 * Service for loading trucks from a CSV file containing parcel data.
 * This service reads parcel names from a file, retrieves corresponding {@link Parcel} objects,
 * and uses {@link LoadTrucksService} to load them into trucks based on a specified algorithm.
 */
@AllArgsConstructor
public class LoadTruckFromFileService {

    private final LoadTrucksService loadTrucksService;

    private final ParcelsCsvFileParser fileParser;

    private final ParcelService parcelService;

    /**
     * Loads trucks by parsing parcel names from a CSV file and using the specified placement algorithm.
     *
     * @param filePath            Path to the input CSV file containing parcel names.
     * @param placementAlgorithm  Algorithm used to place parcels into trucks.
     * @param trucksSizes         List of truck sizes available for loading parcels.
     * @return {@link LoadTrucksResult} containing the loaded trucks and their contents.
     * @throws WrongInputFilePathException if the provided file path is null or empty.
     */
    public LoadTrucksResult loadTrucks(
            String filePath,
            PlacementAlgorithm placementAlgorithm,
            List<Size> trucksSizes
    ) {
        if (filePath == null || filePath.isEmpty()) {
            throw new WrongInputFilePathException("Empty input filepath");
        }

        List<Parcel> parcels = fileParser.parseFromFile(filePath)
                .stream()
                .map(name -> Parcel.fromEntity(parcelService.getParcelByName(name)))
                .toList();

        return loadTrucksService.loadTrucks(parcels, placementAlgorithm, trucksSizes);
    }
}
