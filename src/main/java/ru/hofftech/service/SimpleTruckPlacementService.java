package ru.hofftech.service;

import ru.hofftech.manager.TruckPlacementManager;
import ru.hofftech.model.Parcel;
import ru.hofftech.model.PlaceResult;
import ru.hofftech.model.TruckPlacement;
import ru.hofftech.model.dto.TruckSettingsDto;

import java.util.ArrayList;
import java.util.List;

public class SimpleTruckPlacementService implements TruckPlacementService {

    public PlaceResult placeParcelsToTrucks(List<Parcel> parcels, TruckSettingsDto truckSettingsDto) {
        List<TruckPlacement> truckPlacements = new ArrayList<>();

        for (Parcel parcel : parcels) {
            TruckPlacement truckPlacement = new TruckPlacement(
                    truckSettingsDto.getWidth(),
                    truckSettingsDto.getHeight()
            );

            (new TruckPlacementManager(parcel, truckPlacement)).place();
            truckPlacements.add(truckPlacement);
        }

        return new PlaceResult(truckPlacements);
    }
}
