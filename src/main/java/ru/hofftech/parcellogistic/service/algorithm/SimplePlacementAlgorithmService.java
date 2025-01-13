package ru.hofftech.parcellogistic.service.algorithm;

import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.LoadParcelsResult;
import ru.hofftech.parcellogistic.model.Truck;
import ru.hofftech.parcellogistic.settings.TruckSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimplePlacementAlgorithmService implements PlacementAlgorithmService {

    private final TruckPlacementManager truckPlacementManager;

    public SimplePlacementAlgorithmService(TruckPlacementManager truckPlacementManager) {
        this.truckPlacementManager = truckPlacementManager;
    }

    public LoadParcelsResult placeParcelsToTrucks(List<Parcel> parcels, List<TruckSettings> trucksSettings) {
        if (parcels.size() > trucksSettings.size()) {
            throw new IndexOutOfBoundsException("Out of count trucks");
        }

        List<Truck> trucks = new ArrayList<>();
        Iterator<TruckSettings> trucksSettingsIterator = trucksSettings.iterator();

        for (Parcel parcel : parcels) {
            TruckSettings truckSettings = trucksSettingsIterator.next();
            Truck truck = new Truck(truckSettings);

            truckPlacementManager.place(parcel, truck);
            trucks.add(truck);
        }

        return new LoadParcelsResult(trucks);
    }
}
