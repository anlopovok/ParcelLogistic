package ru.hofftech.parcellogistic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.parcellogistic.enums.OutType;
import ru.hofftech.parcellogistic.model.Coordinate;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.Size;
import ru.hofftech.parcellogistic.model.Truck;
import ru.hofftech.parcellogistic.model.json.ParcelJsonNode;
import ru.hofftech.parcellogistic.model.json.TruckJsonNode;
import ru.hofftech.parcellogistic.util.JsonReader;
import ru.hofftech.parcellogistic.util.JsonWriter;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OutputLoadedTrucksServiceTest {

    private OutputLoadedTrucksService outputLoadedTrucksService;

    private JsonReader jsonReader;

    @BeforeEach
    public void setUp() {
        outputLoadedTrucksService = new OutputLoadedTrucksService(new JsonWriter());

        jsonReader = new JsonReader();
    }

    @Test
    void testFile_whenOutputAsFile_thenTrucksFileSaved() throws URISyntaxException {
        Truck truck = new Truck(new Size(3, 3));
        truck.addParcel(
                new Parcel("Посылка тип 1", List.of("1")),
                List.of(new Coordinate(0, 0))
        );

        LoadTrucksResult loadTrucksResult = new LoadTrucksResult(List.of(truck));
        outputLoadedTrucksService.out(loadTrucksResult, OutType.JSON, "trucks.json");

        Path path = Paths.get(ClassLoader.getSystemResource("").toURI()).resolve("trucks.json");
        assertThat(Files.exists(path)).isTrue();
    }

    @Test
    void testFile_whenOutputAsFile_thenJsonEquals() {
        Parcel parcel = new Parcel("Посылка тип 1", List.of("1"));
        List<Coordinate> coordinates =  List.of(new Coordinate(0, 0));
        Truck truck = new Truck(new Size(3, 3));
        truck.addParcel(
                parcel,
               coordinates
        );

        LoadTrucksResult loadTrucksResult = new LoadTrucksResult(List.of(truck));
        outputLoadedTrucksService.out(loadTrucksResult, OutType.JSON, "trucks.json");

        List<ParcelJsonNode> comparingParcels = List.of(
                ParcelJsonNode.builder()
                        .name("Посылка тип 1")
                        .coordinates(List.of(List.of(0, 0)))
                        .build()
        );

        TruckJsonNode[] expectedTrucks = List.of(
                TruckJsonNode.builder()
                        .truckType("3x3")
                        .parcels(comparingParcels)
                        .build()
        ).toArray(TruckJsonNode[]::new);

        TruckJsonNode[] actualTrucks = jsonReader.readValue("trucks.json", TruckJsonNode[].class);

        assertThat(actualTrucks).usingRecursiveComparison().isEqualTo(expectedTrucks);
    }

    @Test
    void testFile_whenOutputAsText_thenTextEquals() {
        Parcel parcel = new Parcel("Посылка тип 1", List.of("1"));
        List<Coordinate> coordinates =  List.of(new Coordinate(2, 0));
        Truck truck = new Truck(new Size(3, 3));
        truck.addParcel(
                parcel,
                coordinates
        );

        LoadTrucksResult loadTrucksResult = new LoadTrucksResult(List.of(truck));
        OutputResult outputResult = outputLoadedTrucksService.out(loadTrucksResult, OutType.TEXT, null);

        assertThat(outputResult.getMessage()).isEqualTo(
                """
                        Кузов:
                        3x3
                        +   +
                        +   +
                        +1  +
                        +++++
                        Посылка тип 1:
                        [(2,0)]
                        """
        );
    }
}