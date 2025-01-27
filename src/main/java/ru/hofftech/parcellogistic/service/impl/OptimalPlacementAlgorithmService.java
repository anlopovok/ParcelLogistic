package ru.hofftech.parcellogistic.service.impl;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.exception.OutOfCountTrucksException;
import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.Truck;
import ru.hofftech.parcellogistic.service.PlacementAlgorithmService;
import ru.hofftech.parcellogistic.model.Size;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service implementation of the optimal placement algorithm for distributing parcels among trucks.
 */
@AllArgsConstructor
public class OptimalPlacementAlgorithmService implements PlacementAlgorithmService {

    private final TruckPlacementManager truckPlacementManager;

    /**
     * Places parcels into trucks using an optimal algorithm.
     *
     * @param parcels the list of parcels to be placed
     * @param trucksSizes the settings defining truck capacities
     * @return a result object containing the truck load details
     * @throws OutOfCountTrucksException if a parcel cannot be placed in any available truck
     */
    public LoadTrucksResult placeParcelsToTrucks(List<Parcel> parcels, List<Size> trucksSizes) {
        List<Truck> trucks = new ArrayList<>();
        Iterator<Size> trucksSizesIterator = trucksSizes.iterator();

        int counter = 0;
        for (Parcel parcel : parcels) {
            int currentCounter = counter;
            Truck currentTruck = trucks.stream()
                    .filter(truck -> truckPlacementManager.canPlace(parcel, truck))
                    .findFirst().orElseGet(() -> createTruck(
                            trucksSizesIterator,
                            trucks,
                            parcels.subList(currentCounter, parcels.size())
                    ));

            truckPlacementManager.place(parcel, currentTruck);
            counter++;
        }

        return new LoadTrucksResult(trucks);
    }

    private Truck createTruck(
            Iterator<Size> trucksSizesIterator,
            List<Truck> trucks,
            List<Parcel> notPlacedParcels
    ) {
        if (!trucksSizesIterator.hasNext()) {
            throw new OutOfCountTrucksException(notPlacedParcels);
        }

        Size size = trucksSizesIterator.next();
        trucks.add(
                new Truck(size)
        );

        return trucks.getLast();
    }
}
