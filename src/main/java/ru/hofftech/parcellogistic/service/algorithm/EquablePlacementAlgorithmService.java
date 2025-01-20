package ru.hofftech.parcellogistic.service.algorithm;

import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.LoadParcelsResult;
import ru.hofftech.parcellogistic.model.Truck;
import ru.hofftech.parcellogistic.settings.TruckSettings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EquablePlacementAlgorithmService implements PlacementAlgorithmService {

    private final TruckPlacementManager truckPlacementManager;

    public EquablePlacementAlgorithmService(TruckPlacementManager truckPlacementManager) {
        this.truckPlacementManager = truckPlacementManager;
    }

    public LoadParcelsResult placeParcelsToTrucks(List<Parcel> parcels, List<TruckSettings> trucksSettings) {
        List<Truck> trucks = new ArrayList<>();

        for (TruckSettings truckSettings : trucksSettings) {
            trucks.add(
                    new Truck(truckSettings)
            );
        }

        parcels.sort(Comparator.comparingInt(Parcel::getWeight).reversed());
        for (Parcel parcel : parcels) {
            Truck currentTruck = trucks.stream()
                    .sorted(Comparator.comparingInt(Truck::getWeight))
                    .filter(truck -> truckPlacementManager.canPlace(parcel, truck))
                    .findAny()
                    .orElseThrow(() -> new IndexOutOfBoundsException("Out of count trucks"));

            truckPlacementManager.place(parcel, currentTruck);
        }

        return new LoadParcelsResult(trucks);
    }
}
