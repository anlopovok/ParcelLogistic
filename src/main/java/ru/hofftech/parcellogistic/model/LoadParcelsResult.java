package ru.hofftech.parcellogistic.model;

import ru.hofftech.parcellogistic.model.dto.ParcelsJsonNodeDto;
import ru.hofftech.parcellogistic.model.dto.TrucksJsonNodeDto;

import java.util.List;

public class LoadParcelsResult {

    private final List<Truck> trucks;

    public LoadParcelsResult(List<Truck> trucks) {
        this.trucks = trucks;
    }

    public int trucksCount() {
        return trucks.size();
    }

    public boolean isSuccess() {
        return trucksCount() > 0;
    }

    public String toString() {
        return String.join(
                String.format("%n"),
                trucks.stream().map(Truck::toString).toList()
        );
    }

    public TrucksJsonNodeDto toJson() {
        List<ParcelsJsonNodeDto> parcelsJsonNodes = trucks.stream().map(Truck::toJson).toList();

        return TrucksJsonNodeDto.builder().trucks(parcelsJsonNodes).build();
    }
}
