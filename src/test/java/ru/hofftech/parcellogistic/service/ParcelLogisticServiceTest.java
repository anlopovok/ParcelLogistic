package ru.hofftech.parcellogistic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.model.PlaceResult;
import ru.hofftech.parcellogistic.util.TxtParser;
import ru.hofftech.parcellogistic.util.TxtReader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParcelLogisticServiceTest {
    private ParcelLogisticService parcelLogisticService;

    @BeforeEach
    public void setUp() {
        this.parcelLogisticService = new ParcelLogisticService(
                new TruckPlacementServiceFactory(),
                new TxtParser(new TxtReader())
        );
    }

    @Test
    void testFile_whenUnknownFileName_ThenThrowsError() {
        assertThatThrownBy(
                () -> parcelLogisticService.placeParcelsFromFile("test.txt", PlacementAlgorithm.SIMPLE)
        ).isInstanceOf(IOException.class);
    }

    @Test
    void testTruckPlacement_whenSimpleAlgorithm_thenTrucksCountEqualToParcelsCount() {
        PlaceResult placeResult = parcelLogisticService
                .placeParcelsFromFile("parcels.txt", PlacementAlgorithm.SIMPLE);
        assertThat(placeResult.trucksCount()).isEqualTo(6);

        PlaceResult placeResult2 = parcelLogisticService
                .placeParcelsFromFile("parcels2.txt", PlacementAlgorithm.SIMPLE);
        assertThat(placeResult2.trucksCount()).isEqualTo(8);
    }

    @Test
    void testTruckPlacement_whenOptimalAlgorithm_thenTrucksCountLessThenParcelsCount() {
        PlaceResult placeResult = parcelLogisticService
                .placeParcelsFromFile("parcels.txt", PlacementAlgorithm.OPTIMAL);
        assertThat(placeResult.trucksCount()).isEqualTo(1);

        PlaceResult placeResult2 = parcelLogisticService
                .placeParcelsFromFile("parcels2.txt", PlacementAlgorithm.OPTIMAL);
        assertThat(placeResult2.trucksCount()).isEqualTo(2);
    }

    @Test
    void testTruckPlacementContent_whenOptimalAlgorithmResult_thenResultEqualToString() {
        PlaceResult placeResult = parcelLogisticService
                .placeParcelsFromFile("parcels.txt", PlacementAlgorithm.OPTIMAL);
        assertThat(placeResult.toString().equals("""
                +      +
                +333   +
                +55555 +
                +99911 +
                +999666+
                +999666+
                ++++++++
                
                """)).isTrue();

        PlaceResult placeResult2 = parcelLogisticService
                .placeParcelsFromFile("parcels2.txt", PlacementAlgorithm.OPTIMAL);
        assertThat(placeResult2.toString().equals("""
                +55555 +
                +4444  +
                +8888  +
                +8888  +
                +22777 +
                +117777+
                ++++++++
                
                +      +
                +      +
                +      +
                +999   +
                +999   +
                +999   +
                ++++++++
                
                """)).isTrue();
    }
}