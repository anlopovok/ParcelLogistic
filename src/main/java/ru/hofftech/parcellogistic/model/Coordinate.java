package ru.hofftech.parcellogistic.model;

import java.util.List;

public record Coordinate (int line, int column) {

    public String toFormattedString() {
        return String.format("(%d,%d)", line, column);
    }

    public List<Integer> toJson() {
        return List.of(line, column);
    }
}
