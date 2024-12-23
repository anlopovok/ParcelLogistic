package ru.hofftech.parcellogistic.model;

import java.util.List;

public class PlaceResult {
    private final List<TruckPlacement> truckPlacements;

    public PlaceResult(List<TruckPlacement> truckPlacements) {
        this.truckPlacements = truckPlacements;
    }

    public int trucksCount() {
        return truckPlacements.size();
    }

    public boolean isSuccess() {
        return trucksCount() > 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        truckPlacements.forEach(placement -> stringBuilder.append(placement.toString()));

        return stringBuilder.toString();
    }
}
