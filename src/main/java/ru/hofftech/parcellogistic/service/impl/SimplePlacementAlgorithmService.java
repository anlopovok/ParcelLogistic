package ru.hofftech.parcellogistic.service.impl;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.exception.OutOfCountTrucksException;
import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.Size;
import ru.hofftech.parcellogistic.model.Truck;
import ru.hofftech.parcellogistic.service.PlacementAlgorithmService;

import java.util.Comparator;
import java.util.List;

/**
 * Service implementation of a simple placement algorithm for distributing parcels among trucks.
 */
@AllArgsConstructor
public class SimplePlacementAlgorithmService implements PlacementAlgorithmService {

    private final TruckPlacementManager truckPlacementManager;

    /**
     * Places parcels into trucks using a simple algorithm.
     *
     * @param parcels the list of parcels to be placed
     * @param trucksSizes the settings defining truck capacities
     * @return a result object containing the truck load details
     * @throws OutOfCountTrucksException if there are more parcels than available trucks
     */
    public LoadTrucksResult placeParcelsToTrucks(List<Parcel> parcels, List<Size> trucksSizes) {
        if (parcels.size() > trucksSizes.size()) {
            throw new OutOfCountTrucksException(parcels.subList(trucksSizes.size() - 1, parcels.size()));
        }

        List<Truck> trucks = trucksSizes.stream().map(Truck::new).toList();

        int counter = 0;
        for (Parcel parcel : parcels) {
            int currentCounter = counter;
            Truck currentTruck = trucks.stream()
                    .sorted(Comparator.comparingInt(Truck::getWeight))
                    .filter(truck -> !truck.hasParcels() && truckPlacementManager.canPlace(parcel, truck))
                    .findAny()
                    .orElseThrow(() ->
                            new OutOfCountTrucksException(parcels.subList(currentCounter, parcels.size()))
                    );

            truckPlacementManager.place(parcel, currentTruck);
            counter++;
        }

        return new LoadTrucksResult(trucks);
    }
}
