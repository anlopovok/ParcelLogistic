package ru.hofftech.model;

import java.util.List;

public final class Parcel {

    private final List<String> lines;

    private int firstLineWidth;

    private int width;

    private int height;

    public Parcel(List<String> lines) {
        this.lines = lines;
        this.firstLineWidth = lines.stream().findFirst().get().length();
        this.width = lines.stream().mapToInt(i -> i.length()).max().getAsInt();
        this.height = lines.size();
    }

    public int getFirstLineWidth() {
        return firstLineWidth;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getAsCharArray() {
        char[][] chars = new char[height][width];

        for (int i = 0; i < height; i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                chars[i][j] = line.charAt(j);
            }
        }
        return chars;
    }
}
