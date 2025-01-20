package ru.hofftech.parcellogistic.service.algorithm;

import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.LoadParcelsResult;
import ru.hofftech.parcellogistic.model.Truck;
import ru.hofftech.parcellogistic.settings.TruckSettings;

import java.util.*;
import java.util.stream.IntStream;

public class TightPlacementAlgorithmService implements PlacementAlgorithmService {

    private final TruckPlacementManager truckPlacementManager;

    public TightPlacementAlgorithmService(TruckPlacementManager truckPlacementManager) {
        this.truckPlacementManager = truckPlacementManager;
    }

    public LoadParcelsResult placeParcelsToTrucks(List<Parcel> parcels, List<TruckSettings> trucksSettings) {
        Iterator<TruckSettings> trucksSettingsIterator = trucksSettings.iterator();

        List<Truck> trucks = recursivelyPlaceParcelsToTrucks(new ArrayList<>(), parcels, trucksSettingsIterator, true);

        return new LoadParcelsResult(trucks);
    }

    private List<Truck> recursivelyPlaceParcelsToTrucks(
            List<Truck> trucks,
            List<Parcel> parcels,
            Iterator<TruckSettings> trucksSettingsIterator,
            boolean isCreateTruck
    ) {
        if (isCreateTruck) {
            if (!trucksSettingsIterator.hasNext()) {
                throw new IndexOutOfBoundsException("Out of count trucks");
            }

            TruckSettings truckSettings = trucksSettingsIterator.next();
            trucks.add(
                    new Truck(truckSettings)
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
                trucksSettingsIterator,
                isCreateTruck
        );
    }

    private List<Parcel> placeParcelsToTruck(List<Parcel> parcels, Truck truck) {

        int neededWidth = truck.getWidth() - truck.getFilledWidth(truck.getCurrentLine());

        Comparator<Parcel> widthComparator = Comparator.comparingInt(parcel -> parcel.getWidth() <= neededWidth ? 0 : 1);
        Comparator<Parcel> weightComparator = Comparator.comparingInt(Parcel::getWeight).reversed();
        parcels.sort(widthComparator.thenComparing(weightComparator));

        OptionalInt indexOptional = IntStream.range(0, parcels.size())
                .filter(index -> truckPlacementManager.canPlace(parcels.get(index), truck))
                .findFirst();

        List<Parcel> notPlacedParcels = new ArrayList<>(parcels);
        indexOptional.ifPresent(index -> {
            truckPlacementManager.place(parcels.get(index), truck);
            notPlacedParcels.remove(index);
        });

        return notPlacedParcels;
    }
}
