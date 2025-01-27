package ru.hofftech.parcellogistic.model;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UnloadTrucksResult {

    private static final String DELIMITER = ";";

    private final List<ParcelEntity> parcels;

    public List<List<String>> toCsv(boolean withCount) {
        return getAsTable(withCount);
    }

    public String getAsString(boolean withCount) {
        return getAsTable(withCount).stream()
                .map(row -> String.join(DELIMITER, row))
                .collect(Collectors.joining("\n"));
    }

    private List<List<String>> getAsTable(boolean withCount) {
        if (withCount) {
            return parcels.stream()
                    .collect(Collectors.groupingBy(ParcelEntity::name, Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey))
                    .map(entry -> List.of(
                            String.format("\"%s\"", entry.getKey()),
                            String.valueOf(entry.getValue()
                            )))
                    .toList();
        }

        return parcels.stream()
                .map(parcel -> List.of(String.format("\"%s\"", parcel.name())))
                .toList();
    }
}
