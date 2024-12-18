package ru.hofftech.service;

import ru.hofftech.model.Parcel;
import ru.hofftech.model.PlaceResult;
import ru.hofftech.model.dto.TruckSettingsDto;

import java.util.List;

public interface TruckPlacementService {

    public PlaceResult placeParcelsToTrucks(List<Parcel> parcels, TruckSettingsDto truckSettingsDto);
}
