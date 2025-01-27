package ru.hofftech.parcellogistic.model;

public record Size(int width, int height) {

    public String toFormattedString() {
        return String.format("%dx%d", width, height);
    }
}
