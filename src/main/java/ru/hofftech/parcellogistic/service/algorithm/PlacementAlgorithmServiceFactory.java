package ru.hofftech.parcellogistic.service.algorithm;

import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.manager.TruckPlacementManager;

public class PlacementAlgorithmServiceFactory {

    public PlacementAlgorithmService getPlacementAlgorithmService(PlacementAlgorithm placementAlgorithm) {
        return switch (placementAlgorithm) {
            case SIMPLE -> new SimplePlacementAlgorithmService(new TruckPlacementManager());
            case OPTIMAL -> new OptimalPlacementAlgorithmService(new TruckPlacementManager());
            case EQUABLE -> new EquablePlacementAlgorithmService(new TruckPlacementManager());
            case TIGHT -> new TightPlacementAlgorithmService(new TruckPlacementManager());
        };
    }
}
