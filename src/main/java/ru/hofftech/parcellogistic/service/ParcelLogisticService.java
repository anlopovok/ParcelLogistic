package ru.hofftech.parcellogistic.service;

import lombok.SneakyThrows;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.PlaceResult;
import ru.hofftech.parcellogistic.model.dto.TruckSettingsDto;
import ru.hofftech.parcellogistic.util.FileParser;

import java.util.List;

public final class ParcelLogisticService {

    private static final int TRUCK_WIDTH = 6;

    private static final int TRUCK_HEIGHT = 6;

    private final FileParser fileParser;

    private final TruckPlacementServiceFactory truckPlacementServiceFactory;

    public ParcelLogisticService(TruckPlacementServiceFactory truckPlacementServiceFactory, FileParser fileParser) {
        this.truckPlacementServiceFactory = truckPlacementServiceFactory;
        this.fileParser = fileParser;
    }

    @SneakyThrows
    public PlaceResult placeParcelsFromFile(
            String filePath,
            PlacementAlgorithm placementAlgorithm
    ) {
        List<Parcel> parcels = fileParser.parseParcelsFromFile(filePath);

        TruckPlacementService truckPlacementService = truckPlacementServiceFactory
                .getTruckPlacementService(placementAlgorithm);

        return truckPlacementService.placeParcelsToTrucks(
                parcels,
                new TruckSettingsDto(TRUCK_WIDTH, TRUCK_HEIGHT)
        );
    }
}
