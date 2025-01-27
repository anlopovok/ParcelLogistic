package ru.hofftech.parcellogistic.exception;

public class InvalidFileExtension extends RuntimeException {

    public InvalidFileExtension(String filePath, String extension) {
        super(String.format("Invalid file extension %s. Extension should be %s", filePath, extension));
    }
}
