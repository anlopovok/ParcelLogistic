package ru.hofftech.parcellogistic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.parcellogistic.model.UnloadTrucksResult;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;
import ru.hofftech.parcellogistic.util.CsvReader;
import ru.hofftech.parcellogistic.util.CsvWriter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OutputUnloadedTrucksServiceTest {

    private OutputUnloadedTrucksService outputUnloadedTrucksService;

    private CsvReader csvReader;

    @BeforeEach
    public void setUp() {
        outputUnloadedTrucksService = new OutputUnloadedTrucksService(
                new CsvWriter()
        );
        csvReader = new CsvReader();
    }

    @Test
    void testFile_whenOutputWithoutCount_thenParcelsFileSaved() {
        List<ParcelEntity> parcels = List.of(
                ParcelEntity.builder()
                        .id("Посылка тип 1")
                        .name("Посылка тип 1")
                        .content(List.of("1"))
                        .build()
        );
        UnloadTrucksResult unloadTrucksResult = new UnloadTrucksResult(parcels);
        outputUnloadedTrucksService.out(unloadTrucksResult, "parcels.csv", false);

        assertThat(csvReader.readColumns("parcels.csv", ";"))
                .isEqualTo(List.of(List.of("\"Посылка тип 1\"")));
    }

    @Test
    void testFile_whenOutputWithCount_thenParcelsFileSaved() {
        List<ParcelEntity> parcels = List.of(
                ParcelEntity.builder()
                        .id("Посылка тип 1")
                        .name("Посылка тип 1")
                        .content(List.of("1"))
                        .build(),
                ParcelEntity.builder()
                        .id("Посылка тип 2")
                        .name("Посылка тип 2")
                        .content(List.of("2"))
                        .build(),
                ParcelEntity.builder()
                        .id("Посылка тип 1")
                        .name("Посылка тип 1")
                        .content(List.of("1"))
                        .build()
        );
        UnloadTrucksResult unloadTrucksResult = new UnloadTrucksResult(parcels);
        outputUnloadedTrucksService.out(unloadTrucksResult, "parcels.csv", true);

        assertThat(csvReader.readColumns("parcels.csv", ";"))
                .isEqualTo(List.of(
                        List.of("\"Посылка тип 1\"", "2"),
                        List.of("\"Посылка тип 2\"", "1")
                ));
    }
}