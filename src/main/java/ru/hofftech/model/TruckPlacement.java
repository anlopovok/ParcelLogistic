package ru.hofftech.model;

public class TruckPlacement {

    private char[][] placement;

    private int width;

    private int height;

    public TruckPlacement(int width, int height) {
        this.width = width;
        this.height = height;
        this.placement = new char[height][width];
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getFilledWidth(int line) {
        int fillledWidth = 0;
        while (!isOutOfBounds(line, fillledWidth) && isFilledAtPosition(line, fillledWidth)) {
            fillledWidth++;
        }

        return fillledWidth;
    }

    public char getCharAtPosition(int i, int j) {
        return placement[i][j];
    }

    public void setCharAtPosition(int line, int column, char value) {
        placement[line][column] = value;
    }

    public boolean isFilledAtPosition(int line, int column) {
        return placement[line][column] != 0;
    }

    public boolean isHeightLessThan(int line) {
        return line > height;
    }

    public boolean isOutOfBounds(int line, int column) {
        return line < 0 || line >= height || column < 0 || column >= width;
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
            stringBuilder.append('\n');
        }
        stringBuilder.append('\n');

        return stringBuilder.toString();
    }
}
