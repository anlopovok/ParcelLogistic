package ru.hofftech.parcellogistic.exception;

public class DuplicateParcelException extends RuntimeException {

    public DuplicateParcelException(String parcelId) {
        super(String.format("Parcel \"%s\" is equal existing parcel", parcelId));
    }
}
