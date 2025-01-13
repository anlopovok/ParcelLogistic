package ru.hofftech.parcellogistic.service.algorithm;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.LoadParcelsResult;
import ru.hofftech.parcellogistic.model.Truck;
import ru.hofftech.parcellogistic.settings.TruckSettings;

import java.util.*;

@Slf4j
public class OptimalPlacementAlgorithmService implements PlacementAlgorithmService {

    private final TruckPlacementManager truckPlacementManager;

    public OptimalPlacementAlgorithmService(TruckPlacementManager truckPlacementManager) {
        this.truckPlacementManager = truckPlacementManager;
    }

    public LoadParcelsResult placeParcelsToTrucks(List<Parcel> parcels, List<TruckSettings> trucksSettings) {
        List<Truck> trucks = new ArrayList<>();
        Iterator<TruckSettings> trucksSettingsIterator = trucksSettings.iterator();

        for (Parcel parcel : parcels) {
            Truck currentTruck = trucks.stream()
                    .filter(truck -> truckPlacementManager.canPlace(parcel, truck))
                    .findFirst().orElseGet(() -> {
                        if (!trucksSettingsIterator.hasNext()) {
                            throw new IndexOutOfBoundsException("Out of count trucks");
                        }
                        TruckSettings truckSettings = trucksSettingsIterator.next();
                        trucks.add(
                                new Truck(truckSettings)
                        );
                        return trucks.getLast();
                    });

            truckPlacementManager.place(parcel, currentTruck);
        }

        return new LoadParcelsResult(trucks);
    }
}
