package ru.hofftech.parcellogistic.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.PlaceResult;
import ru.hofftech.parcellogistic.model.TruckPlacement;
import ru.hofftech.parcellogistic.model.dto.TruckSettingsDto;

import java.util.*;

@Slf4j
public class OptimalTruckPlacementService implements TruckPlacementService {

    private final TruckPlacementManager truckPlacementManager;

    public OptimalTruckPlacementService(TruckPlacementManager truckPlacementManager) {
        this.truckPlacementManager = truckPlacementManager;
    }

    public PlaceResult placeParcelsToTrucks(List<Parcel> parcels, TruckSettingsDto truckSettingsDto) {
        List<TruckPlacement> truckPlacements = new ArrayList<>();

        for (Parcel parcel : parcels) {
            TruckPlacement currentPlacement = truckPlacements.stream()
                    .filter(t -> truckPlacementManager.canPlace(parcel, t))
                    .findFirst().orElseGet(() -> {
                        truckPlacements.add(
                                new TruckPlacement(truckSettingsDto.getWidth(), truckSettingsDto.getHeight())
                        );
                        return truckPlacements.getLast();
                    });

            truckPlacementManager.place(parcel, currentPlacement);
        }

        return new PlaceResult(truckPlacements);
    }
}
