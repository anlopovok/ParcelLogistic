package ru.hofftech.parcellogistic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.parcellogistic.exception.ErrorWhileParsingFileException;
import ru.hofftech.parcellogistic.exception.InvalidFileExtension;
import ru.hofftech.parcellogistic.model.UnloadTrucksResult;
import ru.hofftech.parcellogistic.model.json.ParcelJsonNode;
import ru.hofftech.parcellogistic.model.json.TruckJsonNode;
import ru.hofftech.parcellogistic.repository.ParcelRepository;
import ru.hofftech.parcellogistic.storage.FileParcelStorage;
import ru.hofftech.parcellogistic.util.JsonReader;
import ru.hofftech.parcellogistic.util.JsonWriter;
import ru.hofftech.parcellogistic.util.TrucksJsonFileParser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UnloadTrucksServiceTest {

    private UnloadTrucksService unloadTrucksService;

    private JsonWriter jsonWriter;

    @BeforeEach
    public void setUp() {
        this.unloadTrucksService = new UnloadTrucksService(
                new TrucksJsonFileParser(new JsonReader()),
                new ParcelService(new ParcelRepository(
                        new FileParcelStorage(
                                "database/parcels.json",
                                new JsonReader()
                        )
                ))
        );

        jsonWriter = new JsonWriter();
    }

    @Test
    void testFile_whenUnknownFileName_thenThrowsError() {
        assertThatThrownBy(
                () -> unloadTrucksService.unloadTrucksFromFile("test.json")
        ).isInstanceOf(ErrorWhileParsingFileException.class);
    }

    @Test
    void testFile_whenUnknownFileExtension_thenThrowsError() {
        assertThatThrownBy(
                () -> unloadTrucksService.unloadTrucksFromFile("trucks.txt")
        ).isInstanceOf(InvalidFileExtension.class);
    }

    @Test
    void testTruckLoading_whenTrucksHasParcels_thenResultEqualToList() {
        List<TruckJsonNode> trucks = List.of(
                new TruckJsonNode("3x3", List.of(new ParcelJsonNode("Посылка тип 1", List.of(
                        List.of(0, 0)
                )))),
                new TruckJsonNode("3x3", List.of(new ParcelJsonNode("Посылка тип 9", List.of(
                        List.of(0, 0),
                        List.of(0, 1),
                        List.of(0, 2),
                        List.of(1, 0),
                        List.of(1, 1),
                        List.of(1, 2),
                        List.of(2, 0),
                        List.of(2, 1),
                        List.of(2, 2)
                )))),
                new TruckJsonNode("6x2", List.of(new ParcelJsonNode("Посылка тип 4", List.of(
                        List.of(0, 0),
                        List.of(1, 0),
                        List.of(2, 0),
                        List.of(3, 0)
                ))))
        );
        jsonWriter.save("trucks.json", trucks);

        UnloadTrucksResult unloadTrucksResult = unloadTrucksService
                .unloadTrucksFromFile("trucks.json");
        assertThat(unloadTrucksResult.toCsv(false).equals(List.of(
                List.of("\"Посылка тип 1\""),
                List.of("\"Посылка тип 9\""),
                List.of("\"Посылка тип 4\"")
        ))).isTrue();
    }
}