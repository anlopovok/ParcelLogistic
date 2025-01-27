package ru.hofftech.parcellogistic.exception;

public class ErrorWhileParsingFileException extends RuntimeException {

    public ErrorWhileParsingFileException(String filePath) {
        super(String.format("Error while parsing file %s", filePath));
    }
}