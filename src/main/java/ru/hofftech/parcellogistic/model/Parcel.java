package ru.hofftech.parcellogistic.model;

import java.util.List;

public final class Parcel {

    private final List<String> lines;

    private final int width;

    private final int height;

    public Parcel(List<String> lines) {
        this.lines = lines;
        this.width = lines.stream().mapToInt(String::length).max().getAsInt();
        this.height = lines.size();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getWidthAtLine(int line) {
        return lines.get(line).length();
    }

    public char getCharAtPosition(int line, int column) {
        return lines.get(line).charAt(column);
    }
}
