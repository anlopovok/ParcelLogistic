package ru.hofftech.parcellogistic.exception;

public class ParcelAlreadyExistsException extends RuntimeException {

    public ParcelAlreadyExistsException(String parcelId) {
        super(String.format("Parcel \"%s\" already exists", parcelId));
    }
}