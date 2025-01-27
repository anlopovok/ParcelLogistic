package ru.hofftech.parcellogistic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.exception.ErrorWhileParsingFileException;
import ru.hofftech.parcellogistic.exception.InvalidFileExtension;
import ru.hofftech.parcellogistic.exception.OutOfCountTrucksException;
import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.dto.CommandOptionToSizesDto;
import ru.hofftech.parcellogistic.repository.ParcelRepository;
import ru.hofftech.parcellogistic.service.impl.EquablePlacementAlgorithmService;
import ru.hofftech.parcellogistic.service.impl.OptimalPlacementAlgorithmService;
import ru.hofftech.parcellogistic.service.impl.SimplePlacementAlgorithmService;
import ru.hofftech.parcellogistic.service.impl.TightPlacementAlgorithmService;
import ru.hofftech.parcellogistic.storage.FileParcelStorage;
import ru.hofftech.parcellogistic.util.CsvWriter;
import ru.hofftech.parcellogistic.util.JsonReader;
import ru.hofftech.parcellogistic.util.ParcelsCsvFileParser;
import ru.hofftech.parcellogistic.util.CsvReader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoadTrucksServiceTest {

    private LoadTruckFromFileService loadTruckFromFileService;

    private LoadTruckFromTextService loadTruckFromTextService;

    private CsvWriter csvWriter;

    private final List<String> parcels1 = List.of(
            "Посылка тип 9",
            "Посылка тип 6",
            "Посылка тип 5",
            "Посылка тип 1",
            "Посылка тип 1",
            "Посылка тип 3"
    );

    private final List<String> parcels2 = List.of(
            "Посылка тип 1",
            "Посылка тип 1",
            "Посылка тип 7",
            "Посылка тип 8",
            "Посылка тип 2",
            "Посылка тип 4",
            "Посылка тип 5",
            "Посылка тип 9"
    );

    private final List<String> parcels3 = List.of(
            "Посылка тип 9",
            "Посылка тип 2",
            "Посылка тип 6",
            "Посылка тип 5",
            "Посылка тип 1",
            "Посылка тип 3",
            "Посылка тип 3",
            "Посылка тип 7",
            "Посылка тип 7",
            "Посылка тип 1"
    );

    @BeforeEach
    public void setUp() {
        TruckPlacementManager truckPlacementManager = new TruckPlacementManager();

        LoadTrucksService loadTrucksService = new LoadTrucksService(
                new PlacementAlgorithmServiceFactory(
                        new SimplePlacementAlgorithmService(truckPlacementManager),
                        new OptimalPlacementAlgorithmService(truckPlacementManager),
                        new EquablePlacementAlgorithmService(truckPlacementManager),
                        new TightPlacementAlgorithmService(truckPlacementManager)
                )
        );
        
        ParcelService parcelService = new ParcelService(new ParcelRepository(
                new FileParcelStorage(
                        "database/parcels.json",
                        new JsonReader()
                )
        ));

        loadTruckFromFileService = new LoadTruckFromFileService(
                loadTrucksService,
                new ParcelsCsvFileParser(new CsvReader()),
                parcelService
        );

        loadTruckFromTextService = new LoadTruckFromTextService(
                loadTrucksService,
                parcelService
        );

        csvWriter = new CsvWriter();
    }

    @Test
    void testFile_whenUnknownFileName_thenThrowsError() {
        assertThatThrownBy(
                () -> loadTruckFromFileService.loadTrucks(
                        "test.csv",
                        PlacementAlgorithm.SIMPLE,
                        new CommandOptionToSizesDto("6x6").getSizes()
                )
        ).isInstanceOf(ErrorWhileParsingFileException.class);
    }

    @Test
    void testFile_whenUnknownFileExtension_thenThrowsError() {
        assertThatThrownBy(
                () -> loadTruckFromFileService.loadTrucks(
                        "parcels.txt",
                        PlacementAlgorithm.SIMPLE,
                        new CommandOptionToSizesDto("6x6").getSizes()
                )
        ).isInstanceOf(InvalidFileExtension.class);
    }

    @Test
    void testFile_whenInputFile_thenGetLoadTrucksResult() {
        csvWriter.save(
                "parcels.csv",
                List.of(List.of(
                        "\"Посылка тип 9\"",
                        "\"Посылка тип 6\"",
                        "\"Посылка тип 5\"",
                        "\"Посылка тип 1\"",
                        "\"Посылка тип 1\"",
                        "\"Посылка тип 3\""
                )),
                ";"
        );
        LoadTrucksResult loadTrucksResult = loadTruckFromFileService.loadTrucks(
                "parcels.csv",
                PlacementAlgorithm.SIMPLE,
                new CommandOptionToSizesDto("6x6\\n6x6\\n6x6\\n6x6\\n6x6\\n6x6").getSizes()
        );
        assertThat(loadTrucksResult.trucksCount()).isPositive();
    }

    @Test
    void testTruckPlacement_whenSimpleAlgorithm_thenTrucksCountEqualToParcelsCount() {
        LoadTrucksResult loadTrucksResult = loadTruckFromTextService.loadTrucks(
                parcels1,
                PlacementAlgorithm.SIMPLE,
                new CommandOptionToSizesDto("6x6\\n6x6\\n6x6\\n6x6\\n6x6\\n6x6").getSizes()
        );
        assertThat(loadTrucksResult.trucksCount()).isEqualTo(6);

        LoadTrucksResult loadTrucksResult2 = loadTruckFromTextService.loadTrucks(
                parcels2,
                PlacementAlgorithm.SIMPLE,
                new CommandOptionToSizesDto("6x6\\n6x6\\n6x6\\n6x6\\n6x6\\n6x6\\n6x6\\n6x6").getSizes()
        );
        assertThat(loadTrucksResult2.trucksCount()).isEqualTo(8);
    }

    @Test
    void testTruckPlacement_whenFewTrucks_thenThrowsError() {
        assertThatThrownBy(
                () -> loadTruckFromTextService.loadTrucks(
                        parcels1,
                        PlacementAlgorithm.SIMPLE,
                        new CommandOptionToSizesDto("6x6").getSizes()
                )
        ).isInstanceOf(OutOfCountTrucksException.class);

        assertThatThrownBy(
                () -> loadTruckFromTextService.loadTrucks(
                        parcels2,
                        PlacementAlgorithm.OPTIMAL,
                        new CommandOptionToSizesDto("6x6").getSizes()
                )
        ).isInstanceOf(OutOfCountTrucksException.class);

        assertThatThrownBy(
                () -> loadTruckFromTextService.loadTrucks(
                        parcels3,
                        PlacementAlgorithm.TIGHT,
                        new CommandOptionToSizesDto("6x6").getSizes()
                )
        ).isInstanceOf(OutOfCountTrucksException.class);

        assertThatThrownBy(
                () -> loadTruckFromTextService.loadTrucks(
                        parcels3,
                        PlacementAlgorithm.EQUABLE,
                        new CommandOptionToSizesDto("6x6").getSizes()
                )
        ).isInstanceOf(OutOfCountTrucksException.class);
    }

    @Test
    void testTruckPlacement_whenOptimalAlgorithm_thenTrucksCountLessThenParcelsCount() {
        LoadTrucksResult loadTrucksResult = loadTruckFromTextService.loadTrucks(
                parcels1,
                PlacementAlgorithm.OPTIMAL,
                new CommandOptionToSizesDto("6x6").getSizes()
        );
        assertThat(loadTrucksResult.trucksCount()).isEqualTo(1);

        LoadTrucksResult loadTrucksResult2 = loadTruckFromTextService.loadTrucks(
                parcels2,
                PlacementAlgorithm.OPTIMAL,
                new CommandOptionToSizesDto("6x6\\n6x6").getSizes()
        );
        assertThat(loadTrucksResult2.trucksCount()).isEqualTo(2);
    }

    @Test
    void testTruckPlacementContent_whenOptimalAlgorithmResult_thenResultEqualToString() {
        LoadTrucksResult loadTrucksResult = loadTruckFromTextService.loadTrucks(
                parcels1,
                PlacementAlgorithm.OPTIMAL,
                new CommandOptionToSizesDto("6x6").getSizes()
        );
        assertThat(loadTrucksResult.getAsString().equals("""
                Кузов:
                6x6
                +      +
                +333   +
                +55555 +
                +99911 +
                +999666+
                +999666+
                ++++++++
                Посылка тип 5:
                [(2,0),(2,1),(2,2),(2,3),(2,4)]
                Посылка тип 3:
                [(1,0),(1,1),(1,2)]
                Посылка тип 1:
                [(3,4)]
                Посылка тип 6:
                [(4,3),(4,4),(4,5),(5,3),(5,4),(5,5)]
                Посылка тип 9:
                [(3,0),(3,1),(3,2),(4,0),(4,1),(4,2),(5,0),(5,1),(5,2)]
                """)).isTrue();

        LoadTrucksResult loadTrucksResult2 = loadTruckFromTextService.loadTrucks(
                parcels2,
                PlacementAlgorithm.OPTIMAL,
                new CommandOptionToSizesDto("6x6\\n6x6").getSizes()
        );
        assertThat(loadTrucksResult2.getAsString().equals("""
                Кузов:
                6x6
                +55555 +
                +4444  +
                +8888  +
                +8888  +
                +22777 +
                +117777+
                ++++++++
                Посылка тип 5:
                [(0,0),(0,1),(0,2),(0,3),(0,4)]
                Посылка тип 2:
                [(4,0),(4,1)]
                Посылка тип 1:
                [(5,1)]
                Посылка тип 8:
                [(2,0),(2,1),(2,2),(2,3),(3,0),(3,1),(3,2),(3,3)]
                Посылка тип 4:
                [(1,0),(1,1),(1,2),(1,3)]
                Посылка тип 7:
                [(4,2),(4,3),(4,4),(5,2),(5,3),(5,4),(5,5)]
                ---------------
                6x6
                +      +
                +      +
                +      +
                +999   +
                +999   +
                +999   +
                ++++++++
                Посылка тип 9:
                [(3,0),(3,1),(3,2),(4,0),(4,1),(4,2),(5,0),(5,1),(5,2)]
                """)).isTrue();
    }

    @Test
    void testTruckPlacementContent_whenTightAlgorithmResult_thenResultEqualToString() {
        LoadTrucksResult loadTrucksResult = loadTruckFromTextService.loadTrucks(
                parcels3,
                PlacementAlgorithm.TIGHT,
                new CommandOptionToSizesDto("6x6\\n6x6").getSizes()
        );
        assertThat(loadTrucksResult.getAsString().equals("""
                Кузов:
                6x6
                +555551+
                +777333+
                +777722+
                +999333+
                +999666+
                +999666+
                ++++++++
                Посылка тип 5:
                [(0,0),(0,1),(0,2),(0,3),(0,4)]
                Посылка тип 3:
                [(1,3),(1,4),(1,5)]
                Посылка тип 2:
                [(2,4),(2,5)]
                Посылка тип 1:
                [(0,5)]
                Посылка тип 6:
                [(4,3),(4,4),(4,5),(5,3),(5,4),(5,5)]
                Посылка тип 9:
                [(3,0),(3,1),(3,2),(4,0),(4,1),(4,2),(5,0),(5,1),(5,2)]
                Посылка тип 7:
                [(1,0),(1,1),(1,2),(2,0),(2,1),(2,2),(2,3)]
                ---------------
                6x6
                +      +
                +      +
                +      +
                +      +
                +777   +
                +77771 +
                ++++++++
                Посылка тип 1:
                [(5,4)]
                Посылка тип 7:
                [(4,0),(4,1),(4,2),(5,0),(5,1),(5,2),(5,3)]
                """)).isTrue();
    }

    @Test
    void testTruckPlacementContent_whenEquableAlgorithmResult_thenResultEqualToString() {
        LoadTrucksResult loadTrucksResult = loadTruckFromTextService.loadTrucks(
                parcels3,
                PlacementAlgorithm.EQUABLE,
                new CommandOptionToSizesDto("6x6\\n6x6\\n6x6\\n6x6\\n6x6").getSizes()
        );
        assertThat(loadTrucksResult.getAsString().equals("""
                Кузов:
                6x6
                +      +
                +      +
                +      +
                +999   +
                +999   +
                +999   +
                ++++++++
                Посылка тип 9:
                [(3,0),(3,1),(3,2),(4,0),(4,1),(4,2),(5,0),(5,1),(5,2)]
                ---------------
                6x6
                +      +
                +      +
                +      +
                +      +
                +777   +
                +777722+
                ++++++++
                Посылка тип 2:
                [(5,4),(5,5)]
                Посылка тип 7:
                [(4,0),(4,1),(4,2),(5,0),(5,1),(5,2),(5,3)]
                ---------------
                6x6
                +      +
                +      +
                +      +
                +      +
                +777   +
                +777711+
                ++++++++
                Посылка тип 1:
                [(5,5)]
                Посылка тип 7:
                [(4,0),(4,1),(4,2),(5,0),(5,1),(5,2),(5,3)]
                ---------------
                6x6
                +      +
                +      +
                +      +
                +      +
                +666   +
                +666333+
                ++++++++
                Посылка тип 3:
                [(5,3),(5,4),(5,5)]
                Посылка тип 6:
                [(4,0),(4,1),(4,2),(5,0),(5,1),(5,2)]
                ---------------
                6x6
                +      +
                +      +
                +      +
                +      +
                +333   +
                +55555 +
                ++++++++
                Посылка тип 5:
                [(5,0),(5,1),(5,2),(5,3),(5,4)]
                Посылка тип 3:
                [(4,0),(4,1),(4,2)]
                """)).isTrue();
    }
}