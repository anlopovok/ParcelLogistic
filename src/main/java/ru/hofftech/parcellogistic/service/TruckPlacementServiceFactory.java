package ru.hofftech.parcellogistic.service;

import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.manager.TruckPlacementManager;

public class TruckPlacementServiceFactory {

    public TruckPlacementService getTruckPlacementService(PlacementAlgorithm placementAlgorithm) {
        return switch (placementAlgorithm) {
            case SIMPLE -> new SimpleTruckPlacementService(new TruckPlacementManager());
            case OPTIMAL -> new OptimalTruckPlacementService(new TruckPlacementManager());
        };
    }
}
