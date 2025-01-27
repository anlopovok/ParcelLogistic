package ru.hofftech.parcellogistic.service.impl;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.exception.OutOfCountTrucksException;
import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.Size;
import ru.hofftech.parcellogistic.model.Truck;
import ru.hofftech.parcellogistic.service.PlacementAlgorithmService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Service implementation of a tight placement algorithm for distributing parcels among trucks.
 */
@AllArgsConstructor
public class TightPlacementAlgorithmService implements PlacementAlgorithmService {

    private final TruckPlacementManager truckPlacementManager;

    /**
     * Places parcels into trucks using a tight packing algorithm.
     *
     * @param parcels the list of parcels to be placed
     * @param trucksSizes the settings defining truck capacities
     * @return a result object containing the truck load details
     * @throws OutOfCountTrucksException if there are more parcels than available trucks
     */
    public LoadTrucksResult placeParcelsToTrucks(List<Parcel> parcels, List<Size> trucksSizes) {
        Iterator<Size> trucksSizesIterator = trucksSizes.iterator();

        List<Truck> trucks = recursivelyPlaceParcelsToTrucks(new ArrayList<>(), parcels, trucksSizesIterator, true);

        return new LoadTrucksResult(trucks);
    }

    private List<Truck> recursivelyPlaceParcelsToTrucks(
            List<Truck> trucks,
            List<Parcel> parcels,
            Iterator<Size> trucksSizesIterator,
            boolean isCreateTruck
    ) {
        if (isCreateTruck) {
            if (!trucksSizesIterator.hasNext()) {
                throw new OutOfCountTrucksException(parcels);
            }

            Size size = trucksSizesIterator.next();
            trucks.add(
                    new Truck(size)
            );
        }

        List<Parcel> notPlacedParcels = placeParcelsToTruck(parcels, trucks.getLast());
        if (notPlacedParcels.isEmpty()) {
            return trucks;
        }

        isCreateTruck = parcels.equals(notPlacedParcels);

        return recursivelyPlaceParcelsToTrucks(
                trucks,
                notPlacedParcels,
                trucksSizesIterator,
                isCreateTruck
        );
    }

    private List<Parcel> placeParcelsToTruck(List<Parcel> parcels, Truck truck) {
        int neededWidth = truck.getWidth() - truck.getFilledWidth(truck.getCurrentLine());

        Comparator<Parcel> widthComparator = Comparator.comparingInt(parcel -> parcel.getWidth() <= neededWidth ? 0 : 1);
        Comparator<Parcel> weightComparator = Comparator.comparingInt(Parcel::getWeight).reversed();
        List<Parcel> sortedParcels = parcels.stream()
                .sorted(widthComparator.thenComparing(weightComparator))
                .toList();

        OptionalInt indexOptional = IntStream.range(0, sortedParcels.size())
                .filter(index -> truckPlacementManager.canPlace(sortedParcels.get(index), truck))
                .findFirst();

        List<Parcel> notPlacedParcels = new ArrayList<>(sortedParcels);
        indexOptional.ifPresent(index -> {
            truckPlacementManager.place(sortedParcels.get(index), truck);
            notPlacedParcels.remove(index);
        });

        return notPlacedParcels;
    }
}
