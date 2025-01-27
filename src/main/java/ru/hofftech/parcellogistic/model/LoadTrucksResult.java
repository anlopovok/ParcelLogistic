package ru.hofftech.parcellogistic.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hofftech.parcellogistic.exception.ErrorWhileSerializeJson;
import ru.hofftech.parcellogistic.model.json.ParcelJsonNode;
import ru.hofftech.parcellogistic.model.json.TruckJsonNode;

import java.util.List;
import java.util.Map;

public class LoadTrucksResult {

    private final List<Truck> trucks;

    public LoadTrucksResult(List<Truck> trucks) {
        this.trucks = trucks;
    }

    public int trucksCount() {
        return trucks.size();
    }

    public String getAsString() {
        return String.format("""
                            Кузов:
                            %s
                            """,
                String.join(
                        String.format("%n---------------%n"),
                        trucks.stream().map(this::getTruckAsString).toList()
                )
        );
    }

    public List<TruckJsonNode> getAsJson() {
        return trucks.stream().map(this::getTruckAsJson).toList();
    }

    public String getJsonAsString() {
        List<TruckJsonNode> truckJsonNodes = getAsJson();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(truckJsonNodes);
        } catch (JsonProcessingException e) {
            throw new ErrorWhileSerializeJson("Cannot serialize truck json");
        }
    }

    private String getTruckAsString(Truck truck) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(truck.getSize().toFormattedString());

        List<String> truckContent = truck.getContent();
        for (String truckLine : truckContent) {
            stringBuilder.append(String.format("%n+%s+", truckLine));
        }
        stringBuilder.append(String.format(
                "%n%s",
                "+".repeat(truck.getWidth() + 2)
        ));

        for (Map.Entry<Parcel, List<Coordinate>> entry : truck.getParcelsCoordinates().entrySet()) {
            stringBuilder.append(String.format(
                    "%n%s:%n[%s]",
                    entry.getKey().name(),
                    String.join(",", entry.getValue().stream().map(Coordinate::toFormattedString).toList())
            ));
        }

        return stringBuilder.toString();
    }

    private TruckJsonNode getTruckAsJson(Truck truck) {
        List<ParcelJsonNode> jsonParcels = truck.getParcelsCoordinates().entrySet().stream()
                .map(
                        entry -> ParcelJsonNode.builder()
                                .name(entry.getKey().name())
                                .coordinates(entry.getValue().stream().map(Coordinate::toJson).toList())
                                .build()
                )
                .toList();

        return TruckJsonNode.builder()
                .truckType(truck.getSize().toFormattedString())
                .parcels(jsonParcels)
                .build();
    }
}
