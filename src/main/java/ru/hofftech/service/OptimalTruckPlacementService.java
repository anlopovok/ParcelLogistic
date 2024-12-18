package ru.hofftech.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.exception.OutOfTruckPlacementSize;
import ru.hofftech.manager.TruckPlacementManager;
import ru.hofftech.model.Parcel;
import ru.hofftech.model.PlaceResult;
import ru.hofftech.model.TruckPlacement;
import ru.hofftech.model.dto.TruckSettingsDto;

import java.util.*;

@Slf4j
public class OptimalTruckPlacementService implements TruckPlacementService {

    public PlaceResult placeParcelsToTrucks(List<Parcel> parcels, TruckSettingsDto truckSettingsDto) {
        List<TruckPlacement> truckPlacements = new ArrayList<>();
        TruckPlacement truckPlacement = new TruckPlacement(
                truckSettingsDto.getWidth(),
                truckSettingsDto.getHeight()
        );
        truckPlacements.add(truckPlacement);

        Queue<Parcel> parcelQueue = new LinkedList<>(parcels);
        while (!parcelQueue.isEmpty()) {
            Parcel parcel = parcelQueue.poll();

            Iterator<TruckPlacement> truckIterator = truckPlacements.iterator();
            while (truckIterator.hasNext()) {
                TruckPlacement currentPlacement = truckIterator.next();
                try {
                    (new TruckPlacementManager(parcel, currentPlacement)).place();
                    break;
                } catch (OutOfTruckPlacementSize e) {
                    if (truckIterator.hasNext()) {
                        continue;
                    } else if (parcelQueue.size() > 1) {
                        parcelQueue.add(parcel);
                        continue;
                    }

                    TruckPlacement newPlacement = new TruckPlacement(
                            truckSettingsDto.getWidth(),
                            truckSettingsDto.getHeight()
                    );
                    truckPlacements.add(newPlacement);
                    log.info("Created new truck");

                    (new TruckPlacementManager(parcel, newPlacement)).place();
                    break;
                }
            }
        }

        return new PlaceResult(truckPlacements);
    }
}
