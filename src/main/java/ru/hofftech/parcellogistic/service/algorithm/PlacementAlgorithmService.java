package ru.hofftech.parcellogistic.service.algorithm;

import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.LoadParcelsResult;
import ru.hofftech.parcellogistic.settings.TruckSettings;

import java.util.List;

public interface PlacementAlgorithmService {
    LoadParcelsResult placeParcelsToTrucks(List<Parcel> parcels, List<TruckSettings> trucksSettings);
}
