package ru.hofftech.parcellogistic.exception;

public class ErrorWhileReadingFileException extends RuntimeException {

    public ErrorWhileReadingFileException(String filePath) {
        super(String.format("Error while reading file %s", filePath));
    }
}