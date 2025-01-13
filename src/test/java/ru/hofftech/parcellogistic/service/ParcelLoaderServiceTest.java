package ru.hofftech.parcellogistic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.model.LoadParcelsResult;
import ru.hofftech.parcellogistic.service.algorithm.PlacementAlgorithmServiceFactory;
import ru.hofftech.parcellogistic.util.JsonWriter;
import ru.hofftech.parcellogistic.util.ParcelsTxtFileParser;
import ru.hofftech.parcellogistic.util.TxtReader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParcelLoaderServiceTest {
    private ParcelLoaderService parcelLoaderService;

    @BeforeEach
    public void setUp() {
        this.parcelLoaderService = new ParcelLoaderService(
                new PlacementAlgorithmServiceFactory(),
                new ParcelsTxtFileParser(new TxtReader()),
                new JsonWriter()
        );
    }

    @Test
    void testFile_whenUnknownFileName_thenThrowsError() {
        assertThatThrownBy(
                () -> parcelLoaderService.loadParcelsFromFile("test.txt", PlacementAlgorithm.SIMPLE, 1, false)
        ).isInstanceOf(IOException.class);
    }

    @Test
    void testTruckPlacement_whenSimpleAlgorithm_thenTrucksCountEqualToParcelsCount() {
        LoadParcelsResult loadParcelsResult = parcelLoaderService
                .loadParcelsFromFile("parcels.txt", PlacementAlgorithm.SIMPLE, 6, false);
        assertThat(loadParcelsResult.trucksCount()).isEqualTo(6);

        LoadParcelsResult loadParcelsResult2 = parcelLoaderService
                .loadParcelsFromFile("parcels2.txt", PlacementAlgorithm.SIMPLE, 8, false);
        assertThat(loadParcelsResult2.trucksCount()).isEqualTo(8);
    }

    @Test
    void testTruckPlacement_whenFewTrucks_thenThrowsError() {
        assertThatThrownBy(
                () -> parcelLoaderService.loadParcelsFromFile("parcels.txt", PlacementAlgorithm.SIMPLE, 1, false)
        ).isInstanceOf(IndexOutOfBoundsException.class);

        assertThatThrownBy(
                () -> parcelLoaderService.loadParcelsFromFile("parcels2.txt", PlacementAlgorithm.OPTIMAL, 1, false)
        ).isInstanceOf(IndexOutOfBoundsException.class);

        assertThatThrownBy(
                () -> parcelLoaderService.loadParcelsFromFile("parcels3.txt", PlacementAlgorithm.TIGHT, 1, false)
        ).isInstanceOf(IndexOutOfBoundsException.class);

        assertThatThrownBy(
                () -> parcelLoaderService.loadParcelsFromFile("parcels3.txt", PlacementAlgorithm.EQUABLE, 1, false)
        ).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void testTruckPlacement_whenOptimalAlgorithm_thenTrucksCountLessThenParcelsCount() {
        LoadParcelsResult loadParcelsResult = parcelLoaderService
                .loadParcelsFromFile("parcels.txt", PlacementAlgorithm.OPTIMAL, 1, false);
        assertThat(loadParcelsResult.trucksCount()).isEqualTo(1);

        LoadParcelsResult loadParcelsResult2 = parcelLoaderService
                .loadParcelsFromFile("parcels2.txt", PlacementAlgorithm.OPTIMAL, 2, false);
        assertThat(loadParcelsResult2.trucksCount()).isEqualTo(2);
    }

    @Test
    void testTruckPlacementContent_whenOptimalAlgorithmResult_thenResultEqualToString() {
        LoadParcelsResult loadParcelsResult = parcelLoaderService
                .loadParcelsFromFile("parcels.txt", PlacementAlgorithm.OPTIMAL, 1, false);
        assertThat(loadParcelsResult.toString().equals("""
                +      +
                +333   +
                +55555 +
                +99911 +
                +999666+
                +999666+
                ++++++++
                """)).isTrue();

        LoadParcelsResult loadParcelsResult2 = parcelLoaderService
                .loadParcelsFromFile("parcels2.txt", PlacementAlgorithm.OPTIMAL, 2, false);
        assertThat(loadParcelsResult2.toString().equals("""
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

    @Test
    void testTruckPlacementContent_whenTightAlgorithmResult_thenResultEqualToString() {
        LoadParcelsResult loadParcelsResult2 = parcelLoaderService
                .loadParcelsFromFile("parcels3.txt", PlacementAlgorithm.TIGHT, 2, false);
        assertThat(loadParcelsResult2.toString().equals("""
                +555551+
                +777333+
                +777722+
                +999333+
                +999666+
                +999666+
                ++++++++
                
                +      +
                +      +
                +      +
                +      +
                +777   +
                +77771 +
                ++++++++
                """)).isTrue();
    }

    @Test
    void testTruckPlacementContent_whenEquableAlgorithmResult_thenResultEqualToString() {
        LoadParcelsResult loadParcelsResult2 = parcelLoaderService
                .loadParcelsFromFile("parcels3.txt", PlacementAlgorithm.EQUABLE, 5, false);
        assertThat(loadParcelsResult2.toString().equals("""
                +      +
                +      +
                +      +
                +999   +
                +999   +
                +999   +
                ++++++++
                
                +      +
                +      +
                +      +
                +      +
                +777   +
                +777722+
                ++++++++
                
                +      +
                +      +
                +      +
                +      +
                +777   +
                +777711+
                ++++++++
                
                +      +
                +      +
                +      +
                +      +
                +666   +
                +666333+
                ++++++++
                
                +      +
                +      +
                +      +
                +      +
                +333   +
                +55555 +
                ++++++++
                """)).isTrue();
    }
}