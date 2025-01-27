package ru.hofftech.parcellogistic.exception;

import ru.hofftech.parcellogistic.model.Parcel;

import java.util.List;

public class OutOfCountTrucksException extends RuntimeException {

    public OutOfCountTrucksException(List<Parcel> parcels) {
        super(String.format("Cannot place parcels to trucks. Parcel names: %s",
                parcels.stream().map(Parcel::name)
        ));
    }
}