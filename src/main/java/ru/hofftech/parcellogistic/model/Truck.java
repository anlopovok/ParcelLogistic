package ru.hofftech.parcellogistic.model;

import lombok.Getter;
import ru.hofftech.parcellogistic.model.dto.ParcelJsonNodeDto;
import ru.hofftech.parcellogistic.model.dto.ParcelsJsonNodeDto;
import ru.hofftech.parcellogistic.settings.TruckSettings;

import java.util.ArrayList;
import java.util.List;

public class Truck {

    @Getter
    private final int width;

    @Getter
    private final int height;

    private final List<Parcel> parcels;

    private final char[][] placement;

    public Truck(TruckSettings truckSettings) {
        this.width = truckSettings.width();
        this.height = truckSettings.height();
        this.parcels = new ArrayList<>();
        this.placement = new char[height][width];
    }

    public int getFilledWidth(int line) {
        int filledWidth = 0;
        while (!isOutOfBounds(line, filledWidth) && isFilledAtPosition(line, filledWidth)) {
            filledWidth++;
        }

        return filledWidth;
    }

    public char getCharAtPosition(int line, int column) {
        return placement[line][column];
    }

    public void setCharAtPosition(int line, int column, char value) {
        placement[line][column] = value;
    }

    public boolean isFilledAtPosition(int line, int column) {
        return placement[line][column] != 0;
    }

    public int getCurrentLine() {
        for (int i = height - 1; i > 0; i--) {
            if (getFilledWidth(i) < getWidth()) {
                return i;
            }
        }

        return height - 1;
    }

    public boolean isHeightLessThan(int line) {
        return line > height;
    }

    public boolean isOutOfBounds(int line, int column) {
        return line < 0 || line >= height || column < 0 || column >= width;
    }

    public void add(Parcel parcel) {
        parcels.add(parcel);
    }

    public int getWeight() {
        return parcels.stream().map(Parcel::getWeight).reduce(0, Integer::sum);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= height; i++) {
            for (int j = -1; j <= width; j++) {
                if (i == height || j == -1 || j == width) {
                    stringBuilder.append('+');
                } else {
                    stringBuilder.append(
                        !isFilledAtPosition(i, j) ? ' ' : getCharAtPosition(i, j)
                    );
                }
            }
            stringBuilder.append(String.format("%n"));
        }

        return stringBuilder.toString();
    }

    public ParcelsJsonNodeDto toJson() {
        List<ParcelJsonNodeDto> jsonParcels = parcels.stream().map(Parcel::toJson).toList();

        return ParcelsJsonNodeDto.builder().parcels(jsonParcels).build();
    }
}
