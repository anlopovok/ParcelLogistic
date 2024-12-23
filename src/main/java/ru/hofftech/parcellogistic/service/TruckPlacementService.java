package ru.hofftech.parcellogistic.service;

import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.PlaceResult;
import ru.hofftech.parcellogistic.model.dto.TruckSettingsDto;

import java.util.List;

public interface TruckPlacementService {
    PlaceResult placeParcelsToTrucks(List<Parcel> parcels, TruckSettingsDto truckSettingsDto);
}
