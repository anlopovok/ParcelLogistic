package ru.hofftech.parcellogistic.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Truck {

    @Getter
    private final Size size;

    @Getter
    private final Map<Parcel, List<Coordinate>> parcelsCoordinates;

    private final char[][] placement;

    public Truck(Size size) {
        this.size = size;
        this.parcelsCoordinates = new HashMap<>();
        this.placement = new char[size.height()][size.width()];
    }

    public int getWidth() {
        return size.width();
    }

    public int getHeight() {
        return size.height();
    }

    public int getFilledWidth(int line) {
        int filledWidth = 0;
        while (!isOutOfBounds(line, filledWidth) && isFilledAtPosition(line, filledWidth)) {
            filledWidth++;
        }

        return filledWidth;
    }

    public boolean isFilledAtPosition(int line, int column) {
        return placement[line][column] != 0;
    }

    public int getCurrentLine() {
        for (int i = size.height() - 1; i > 0; i--) {
            if (getFilledWidth(i) < getWidth()) {
                return i;
            }
        }

        return size.height() - 1;
    }

    public boolean isHeightLessThan(int line) {
        return line > size.height();
    }

    public boolean isOutOfBounds(int line, int column) {
        return line < 0 || line >= size.height() || column < 0 || column >= size.width();
    }

    public void addParcel(Parcel parcel, List<Coordinate> coordinates) {
        List<Coordinate> sortedCoordinates = coordinates.stream()
                .sorted(Comparator.comparingInt(Coordinate::line).thenComparing(Coordinate::column))
                .toList();

        Coordinate startCoordinate = sortedCoordinates.getFirst();
        int lineShift = startCoordinate.line();
        int columnShift = startCoordinate.column();

        for (Coordinate coordinate : sortedCoordinates) {
            setCharAtPosition(
                    coordinate.line(),
                    coordinate.column(),
                    parcel.getCharAtPosition(
                            coordinate.line() - lineShift,
                            coordinate.column() - columnShift
                    )
            );
        }

        parcelsCoordinates.put(parcel, coordinates);
    }

    public int getWeight() {
        return parcelsCoordinates.keySet().stream().map(Parcel::getWeight).reduce(0, Integer::sum);
    }

    public boolean hasParcels() {
        return !parcelsCoordinates.isEmpty();
    }

    public List<String> getContent() {
        List<String> content = new ArrayList<>();
        for (int i = 0; i < size.height(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < size.width(); j++) {
                stringBuilder.append(!isFilledAtPosition(i, j) ? ' ' : getCharAtPosition(i, j));
            }
            content.add(stringBuilder.toString());
        }

        return content;
    }

    private void setCharAtPosition(int line, int column, char value) {
        placement[line][column] = value;
    }

    private char getCharAtPosition(int line, int column) {
        return placement[line][column];
    }
}
