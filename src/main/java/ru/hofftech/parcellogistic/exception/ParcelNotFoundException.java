package ru.hofftech.parcellogistic.exception;

public class ParcelNotFoundException extends RuntimeException {

    public ParcelNotFoundException(String parcelId) {
        super(String.format("Parcel \"%s\" not found", parcelId));
    }
}