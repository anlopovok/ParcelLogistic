package ru.hofftech.parcellogistic.model;

import ru.hofftech.parcellogistic.model.dto.ParcelJsonNodeDto;

import java.util.List;

public class LoadTrucksResult {

    private final List<Parcel> parcels;

    public LoadTrucksResult(List<ParcelJsonNodeDto> jsonParcels) {
        this.parcels = jsonParcels.stream().map(jsonParcel -> new Parcel(jsonParcel.getContent())).toList();
    }

    public String toString() {
        List<String> parcelsAsStrings = parcels.stream().map(Parcel::toString).toList();

        return String.join(String.format("%n%n"), parcelsAsStrings);
    }
}
