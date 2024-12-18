package ru.hofftech.service;

import ru.hofftech.enums.PlacementAlgorithm;
import ru.hofftech.model.Parcel;
import ru.hofftech.model.PlaceResult;
import ru.hofftech.model.dto.TruckSettingsDto;
import ru.hofftech.util.FileParser;

import java.util.List;

public final class ParcelLogisticService {

    private static final int TRUCK_WIDTH = 6;

    private static final int TRUCK_HEIGHT = 6;

    private FileParser fileParser;

    public ParcelLogisticService(FileParser fileParser) {
        this.fileParser = fileParser;
    }

    public PlaceResult placeParcelsFromFile(String filePath, PlacementAlgorithm placementAlgorithm) {
        List<Parcel> parcels = fileParser.parseParcelsFromFile(filePath);

        TruckPlacementService truckPlacementService = switch (placementAlgorithm) {
            case SIMPLE -> new SimpleTruckPlacementService();
            case OPTIMAL -> new OptimalTruckPlacementService();
        };

        return truckPlacementService.placeParcelsToTrucks(
            parcels,
            new TruckSettingsDto(TRUCK_WIDTH, TRUCK_HEIGHT)
        );
    }
}
