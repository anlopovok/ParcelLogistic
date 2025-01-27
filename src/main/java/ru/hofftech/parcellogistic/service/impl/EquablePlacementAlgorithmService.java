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
 * Service implementation of the equable placement algorithm for distributing parcels among trucks.
 */
@AllArgsConstructor
public class EquablePlacementAlgorithmService implements PlacementAlgorithmService {

    private final TruckPlacementManager truckPlacementManager;

    /**
     * Places parcels into trucks using an equable algorithm.
     *
     * @param parcels the list of parcels to be placed
     * @param trucksSizes the settings defining truck capacities
     * @return a result object containing the truck load details
     * @throws OutOfCountTrucksException if a parcel cannot be placed in any available truck
     */
    public LoadTrucksResult placeParcelsToTrucks(List<Parcel> parcels, List<Size> trucksSizes) {
        List<Truck> trucks = trucksSizes.stream().map(Truck::new).toList();
        List<Parcel> sortedParcels = parcels.stream()
                .sorted(Comparator.comparingInt(Parcel::getWeight).reversed())
                .toList();

        int counter = 0;
        for (Parcel parcel : sortedParcels) {
            int currentCounter = counter;
            Truck currentTruck = trucks.stream()
                    .sorted(Comparator.comparingInt(Truck::getWeight))
                    .filter(truck -> truckPlacementManager.canPlace(parcel, truck))
                    .findAny()
                    .orElseThrow(() ->
                         new OutOfCountTrucksException(sortedParcels.subList(currentCounter, sortedParcels.size()))
                    );

            truckPlacementManager.place(parcel, currentTruck);
            counter++;
        }

        return new LoadTrucksResult(trucks);
    }
}
