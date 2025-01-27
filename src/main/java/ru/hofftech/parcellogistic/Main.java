package ru.hofftech.parcellogistic;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.config.TelegramBotConfig;
import ru.hofftech.parcellogistic.controller.ConsoleController;
import ru.hofftech.parcellogistic.controller.TelegramBotController;
import ru.hofftech.parcellogistic.handler.TelegramBotHandler;
import ru.hofftech.parcellogistic.manager.TruckPlacementManager;
import ru.hofftech.parcellogistic.repository.ParcelRepository;
import ru.hofftech.parcellogistic.service.LoadTrucksService;
import ru.hofftech.parcellogistic.service.OutputLoadedTrucksService;
import ru.hofftech.parcellogistic.service.OutputUnloadedTrucksService;
import ru.hofftech.parcellogistic.service.ParcelService;
import ru.hofftech.parcellogistic.service.PlacementAlgorithmServiceFactory;
import ru.hofftech.parcellogistic.service.ProcessCommandService;
import ru.hofftech.parcellogistic.service.UnloadTrucksService;
import ru.hofftech.parcellogistic.service.impl.EquablePlacementAlgorithmService;
import ru.hofftech.parcellogistic.service.LoadTruckFromFileService;
import ru.hofftech.parcellogistic.service.LoadTruckFromTextService;
import ru.hofftech.parcellogistic.service.impl.OptimalPlacementAlgorithmService;
import ru.hofftech.parcellogistic.service.impl.SimplePlacementAlgorithmService;
import ru.hofftech.parcellogistic.service.impl.TightPlacementAlgorithmService;
import ru.hofftech.parcellogistic.storage.FileParcelStorage;
import ru.hofftech.parcellogistic.strategy.CommandStrategyFactory;
import ru.hofftech.parcellogistic.util.CommandParser;
import ru.hofftech.parcellogistic.util.CsvWriter;
import ru.hofftech.parcellogistic.util.JsonReader;
import ru.hofftech.parcellogistic.util.JsonWriter;
import ru.hofftech.parcellogistic.util.ParcelsCsvFileParser;
import ru.hofftech.parcellogistic.util.TrucksJsonFileParser;
import ru.hofftech.parcellogistic.util.CsvReader;

import java.util.Scanner;

@Slf4j
public final class Main {

    public static void main(String[] args) {
        log.info("Start application");

        try {
            ProcessCommandService processCommandService = getProcessCommandService();
            listenTelegramBot(processCommandService);
            listenScanner(processCommandService);
        }  catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }

    private static void listenTelegramBot(ProcessCommandService processCommandService) {
        TelegramBotConfig telegramBotConfig = new TelegramBotConfig(Dotenv.load());

        TelegramBotController telegramBotController = new TelegramBotController(
                new TelegramBotHandler(
                        telegramBotConfig.getToken(),
                        telegramBotConfig.getUsername(),
                        processCommandService
                )
        );

        telegramBotController.listen();
    }

    private static void listenScanner(ProcessCommandService processCommandService) {
        ConsoleController consoleController = new ConsoleController(
                new Scanner(System.in),
                processCommandService
        );

        consoleController.listen();
    }

    private static ProcessCommandService getProcessCommandService() {
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
                new FileParcelStorage("database/parcels.json", new JsonReader())
        ));

        return new ProcessCommandService(
                new CommandStrategyFactory(
                        new LoadTruckFromFileService(
                                loadTrucksService,
                                new ParcelsCsvFileParser(new CsvReader()),
                                parcelService
                        ),
                        new LoadTruckFromTextService(loadTrucksService, parcelService),
                        new CommandParser(),
                        new OutputLoadedTrucksService(new JsonWriter()),
                        new UnloadTrucksService(new TrucksJsonFileParser(new JsonReader()), parcelService),
                        new OutputUnloadedTrucksService(new CsvWriter()),
                        parcelService
                )
        );
    }
}