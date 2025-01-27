package ru.hofftech.parcellogistic.service;

import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.Size;

import java.util.List;

public interface PlacementAlgorithmService {

    LoadTrucksResult placeParcelsToTrucks(List<Parcel> parcels, List<Size> trucksSizes);
}
