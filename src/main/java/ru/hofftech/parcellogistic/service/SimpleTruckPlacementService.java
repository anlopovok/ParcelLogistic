package ru.hofftech.parcellogistic.service;

import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.PlaceResult;
import ru.hofftech.parcellogistic.model.TruckPlacement;
import ru.hofftech.parcellogistic.model.dto.TruckSettingsDto;

import java.util.ArrayList;
import java.util.List;

public class SimpleTruckPlacementService implements TruckPlacementService {

    private final TruckPlacementManager truckPlacementManager;

    public SimpleTruckPlacementService(TruckPlacementManager truckPlacementManager) {
        this.truckPlacementManager = truckPlacementManager;
    }

    public PlaceResult placeParcelsToTrucks(List<Parcel> parcels, TruckSettingsDto truckSettingsDto) {
        List<TruckPlacement> truckPlacements = new ArrayList<>();

        for (Parcel parcel : parcels) {
            TruckPlacement truckPlacement = new TruckPlacement(
                    truckSettingsDto.getWidth(),
                    truckSettingsDto.getHeight()
            );

            truckPlacementManager.place(parcel, truckPlacement);
            truckPlacements.add(truckPlacement);
        }

        return new PlaceResult(truckPlacements);
    }
}
