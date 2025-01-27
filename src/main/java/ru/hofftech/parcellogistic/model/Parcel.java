package ru.hofftech.parcellogistic.model;

import ru.hofftech.parcellogistic.model.entity.ParcelEntity;

import java.util.List;

public record Parcel(String name, List<String> content) {

    public static Parcel fromEntity(ParcelEntity entity) {
        return new Parcel(entity.name(), entity.content());
    }

    public int getWidthAtLine(int line) {
        return content.get(line).length();
    }

    public char getCharAtPosition(int line, int column) {
        return content.get(line).charAt(column);
    }

    public int getWeight() {
        return String.join("", content).length();
    }

    public int getWidth() {
        return content.stream()
                .mapToInt(String::length)
                .max()
                .orElse(content.getLast().length());
    }

    public int getHeight() {
        return content.size();
    }
}
