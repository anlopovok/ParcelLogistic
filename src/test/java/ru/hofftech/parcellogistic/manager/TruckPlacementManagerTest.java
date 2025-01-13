package ru.hofftech.parcellogistic.manager;

import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.Truck;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.hofftech.parcellogistic.settings.TruckSettings;


import static org.assertj.core.api.Assertions.assertThat;

class TruckPlacementManagerTest {

    private TruckPlacementManager truckPlacementManager;

    @BeforeEach
    public void setUp() {
        truckPlacementManager = new TruckPlacementManager();
    }

    @Test
    void testPlace_whenCanPlace_ThenTruckContainsParcel() {
        Parcel parcel = new Parcel(List.of("4444"));
        TruckSettings truckSettings = new TruckSettings(6, 6);
        Truck truck = new Truck(truckSettings);

        assertThat(truck.getFilledWidth(5)).isZero();
        truckPlacementManager.place(parcel, truck);
        assertThat(truck.getCharAtPosition(5, 0)).isEqualTo('4');
        assertThat(truck.getCharAtPosition(5, 1)).isEqualTo('4');
        assertThat(truck.getCharAtPosition(5, 2)).isEqualTo('4');
        assertThat(truck.getCharAtPosition(5, 3)).isEqualTo('4');
        assertThat(truck.getFilledWidth(5)).isEqualTo(4);
        assertThat(truck.getFilledWidth(4)).isZero();
    }

    @Test
    void testCanPlace_whenEmptyTruck_ThenReturnTrue() {
        Parcel parcel = new Parcel(List.of("999", "999", "999"));
        TruckSettings truckSettings = new TruckSettings(6, 6);
        Truck truck = new Truck(truckSettings);
        assertThat(truckPlacementManager.canPlace(parcel, truck)).isTrue();
    }

    @Test
    void testCanPlace_whenCanPlace_ThenReturnTrue() {
        Parcel parcel = new Parcel(List.of("999", "999", "999"));
        TruckSettings truckSettings = new TruckSettings(6, 6);
        Truck truck = new Truck(truckSettings);
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                truck.setCharAtPosition(i, j, '9');
            }
        }
        assertThat(truckPlacementManager.canPlace(parcel, truck)).isTrue();
    }

    @Test
    void testCanPlace_whenCanNotPlace_ThenReturnFalse() {
        Parcel parcel = new Parcel(List.of("999", "999", "999"));
        TruckSettings truckSettings = new TruckSettings(6, 6);
        Truck truck = new Truck(truckSettings);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                truck.setCharAtPosition(i, j, '9');
            }
        }
        assertThat(truckPlacementManager.canPlace(parcel, truck)).isFalse();
    }
}