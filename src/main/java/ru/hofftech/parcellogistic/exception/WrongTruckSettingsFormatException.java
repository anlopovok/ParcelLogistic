package ru.hofftech.parcellogistic.exception;

public class WrongTruckSettingsFormatException extends RuntimeException {

    public WrongTruckSettingsFormatException(String message) {
        super(message);
    }
}