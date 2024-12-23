package ru.hofftech.parcellogistic.manager;

import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.TruckPlacement;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


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
        TruckPlacement truckPlacement = new TruckPlacement(6, 6);

        assertThat(truckPlacement.getFilledWidth(5)).isZero();
        truckPlacementManager.place(parcel, truckPlacement);
        assertThat(truckPlacement.getCharAtPosition(5, 0)).isEqualTo('4');
        assertThat(truckPlacement.getCharAtPosition(5, 1)).isEqualTo('4');
        assertThat(truckPlacement.getCharAtPosition(5, 2)).isEqualTo('4');
        assertThat(truckPlacement.getCharAtPosition(5, 3)).isEqualTo('4');
        assertThat(truckPlacement.getFilledWidth(5)).isEqualTo(4);
        assertThat(truckPlacement.getFilledWidth(4)).isZero();
    }

    @Test
    void testCanPlace_whenEmptyTruck_ThenReturnTrue() {
        Parcel parcel = new Parcel(List.of("999", "999", "999"));
        TruckPlacement truckPlacement = new TruckPlacement(6, 6);
        assertThat(truckPlacementManager.canPlace(parcel, truckPlacement)).isTrue();
    }

    @Test
    void testCanPlace_whenCanPlace_ThenReturnTrue() {
        Parcel parcel = new Parcel(List.of("999", "999", "999"));
        TruckPlacement truckPlacement = new TruckPlacement(6, 6);
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                truckPlacement.setCharAtPosition(i, j, '9');
            }
        }
        assertThat(truckPlacementManager.canPlace(parcel, truckPlacement)).isTrue();
    }

    @Test
    void testCanPlace_whenCanNotPlace_ThenReturnFalse() {
        Parcel parcel = new Parcel(List.of("999", "999", "999"));
        TruckPlacement truckPlacement = new TruckPlacement(6, 6);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                truckPlacement.setCharAtPosition(i, j, '9');
            }
        }
        assertThat(truckPlacementManager.canPlace(parcel, truckPlacement)).isFalse();
    }
}