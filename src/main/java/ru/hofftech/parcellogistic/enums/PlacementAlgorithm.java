package ru.hofftech.parcellogistic.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlacementAlgorithm{
    SIMPLE("Одна машина - Одна посылка"),
    OPTIMAL("Оптимальный"),
    EQUABLE("Равномерный"),
    TIGHT("Плотный");

    private final String description;

    public static PlacementAlgorithm getByDescription(String description) {
        for (PlacementAlgorithm algorithm : values()) {
            if (algorithm.description.equals(description)) {
                return algorithm;
            }
        }
        throw new IllegalArgumentException("No PlacementAlgorithm with description: " + description);
    }
}
