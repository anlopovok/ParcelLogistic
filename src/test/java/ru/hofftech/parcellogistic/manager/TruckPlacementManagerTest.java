package ru.hofftech.parcellogistic.manager;

import ru.hofftech.parcellogistic.model.Coordinate;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.Truck;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.hofftech.parcellogistic.model.Size;


import static org.assertj.core.api.Assertions.assertThat;

class TruckPlacementManagerTest {

    private TruckPlacementManager truckPlacementManager;

    @BeforeEach
    public void setUp() {
        truckPlacementManager = new TruckPlacementManager();
    }

    @Test
    void testPlace_whenCanPlace_ThenTruckContainsParcel() {
        Parcel parcel = new Parcel("Посылка тип 4", List.of("4444"));
        Truck truck = new Truck(new Size(6, 6));

        assertThat(truck.getFilledWidth(5)).isZero();
        truckPlacementManager.place(parcel, truck);
        assertThat(truck.getParcelsCoordinates().get(parcel)).isNotEmpty();
        assertThat(truck.getFilledWidth(5)).isEqualTo(4);
        assertThat(truck.getFilledWidth(4)).isZero();
    }

    @Test
    void testCanPlace_whenEmptyTruck_ThenReturnTrue() {
        Parcel parcel = new Parcel("Посылка тип 9", List.of("999", "999", "999"));
        Truck truck = new Truck(new Size(6, 6));
        assertThat(truckPlacementManager.canPlace(parcel, truck)).isTrue();
    }

    @Test
    void testCanPlace_whenCanPlace_ThenReturnTrue() {
        Parcel parcel = new Parcel("Посылка тип 9", List.of("999", "999", "999"));
        Truck truck = new Truck(new Size(6, 3));
        truck.addParcel(parcel, generateCoordinates(0, 0, 2, 2));

        assertThat(truckPlacementManager.canPlace(parcel, truck)).isTrue();
    }

    @Test
    void testCanPlace_whenCanNotPlace_ThenReturnFalse() {
        Parcel parcel = new Parcel("Посылка тип 9", List.of("999", "999", "999"));
        Truck truck = new Truck(new Size(5, 5));
        truck.addParcel(parcel, generateCoordinates(2, 0, 4, 2));
        assertThat(truckPlacementManager.canPlace(parcel, truck)).isFalse();
    }

    private List<Coordinate> generateCoordinates(int startLine, int startColumn, int endLine, int endColumn) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = startLine; i <= endLine; i++) {
            for (int j = startColumn; j <= endColumn; j++) {
                coordinates.add(new Coordinate(i, j));
            }
        }

        return coordinates;
    }
}