package ru.hofftech.parcellogistic.exception;

public class ErrorWhileWritingFileException extends RuntimeException {

    public ErrorWhileWritingFileException(String filePath) {
        super(String.format("Error while writing file %s", filePath));
    }
}