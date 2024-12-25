package ru.hofftech.parcellogistic.model.dto;

public class TruckSettingsDto {

    private final int width;

    private final int height;

    public TruckSettingsDto(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
