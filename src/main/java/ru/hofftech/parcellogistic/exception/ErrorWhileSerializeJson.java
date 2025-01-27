package ru.hofftech.parcellogistic.exception;

public class ErrorWhileSerializeJson extends RuntimeException {

    public ErrorWhileSerializeJson(String message) {
        super(message);
    }
}
