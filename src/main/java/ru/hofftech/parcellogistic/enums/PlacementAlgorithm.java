package ru.hofftech.parcellogistic.enums;

import java.util.Arrays;
import java.util.List;

public enum PlacementAlgorithm{
    SIMPLE,
    OPTIMAL,
    EQUABLE,
    TIGHT;

    public static List<String> getLabels() {
        return Arrays.stream(PlacementAlgorithm.values())
                .map(PlacementAlgorithm::name)
                .map(String::toLowerCase)
                .toList();
    }
}
