package ru.hofftech.parcellogistic.service;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.service.impl.EquablePlacementAlgorithmService;
import ru.hofftech.parcellogistic.service.impl.OptimalPlacementAlgorithmService;
import ru.hofftech.parcellogistic.service.impl.SimplePlacementAlgorithmService;
import ru.hofftech.parcellogistic.service.impl.TightPlacementAlgorithmService;

/**
 * Factory class for providing the appropriate placement algorithm service
 * based on the selected {@link PlacementAlgorithm}.
 *
 * This factory supports the following placement algorithms:
 * {@link SimplePlacementAlgorithmService} - A simple placement strategy
 * {@link OptimalPlacementAlgorithmService} - An optimized placement strategy
 * {@link EquablePlacementAlgorithmService} - A balanced placement strategy
 * {@link TightPlacementAlgorithmService} - A space-efficient placement strategy
 */
@AllArgsConstructor
public class PlacementAlgorithmServiceFactory {

    private final SimplePlacementAlgorithmService simplePlacementAlgorithmService;

    private final OptimalPlacementAlgorithmService optimalPlacementAlgorithmService;

    private final EquablePlacementAlgorithmService equablePlacementAlgorithmService;

    private final TightPlacementAlgorithmService tightPlacementAlgorithmService;

    /**
     * Returns the appropriate {@link PlacementAlgorithmService} implementation
     * based on the given {@link PlacementAlgorithm}.
     *
     * @param placementAlgorithm the selected placement algorithm
     * @return the corresponding {@link PlacementAlgorithmService} implementation
     * @throws IllegalArgumentException if the algorithm is not recognized
     */
    public PlacementAlgorithmService getPlacementAlgorithmService(PlacementAlgorithm placementAlgorithm) {
        return switch (placementAlgorithm) {
            case SIMPLE -> simplePlacementAlgorithmService;
            case OPTIMAL -> optimalPlacementAlgorithmService;
            case EQUABLE -> equablePlacementAlgorithmService;
            case TIGHT -> tightPlacementAlgorithmService;
        };
    }
}
