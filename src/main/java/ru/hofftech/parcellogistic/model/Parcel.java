package ru.hofftech.parcellogistic.model;

import lombok.Getter;
import ru.hofftech.parcellogistic.model.dto.ParcelJsonNodeDto;

import java.util.List;

public final class Parcel {

    private final List<String> lines;

    @Getter
    private final int width;

    @Getter
    private final int height;

    public Parcel(List<String> lines) {
        this.lines = lines;
        this.width = lines.stream()
                .mapToInt(String::length)
                .max()
                .orElse(lines.getLast().length());
        this.height = lines.size();
    }

    public ParcelJsonNodeDto toJson() {
        String joinedLine = String.join("", lines);

        return ParcelJsonNodeDto.builder()
                .symbol(joinedLine.substring(0, 1))
                .content(lines)
                .build();
    }

    public String toString() {
        return String.join(String.format("%n"), lines);
    }

    public int getWidthAtLine(int line) {
        return lines.get(line).length();
    }

    public char getCharAtPosition(int line, int column) {
        return lines.get(line).charAt(column);
    }

    public int getWeight() {
        return String.join("", lines).length();
    }
}
