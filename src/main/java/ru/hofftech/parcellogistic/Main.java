package ru.hofftech.parcellogistic;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.controller.ConsoleController;
import ru.hofftech.parcellogistic.service.ParcelLoaderService;
import ru.hofftech.parcellogistic.service.TruckLoaderService;
import ru.hofftech.parcellogistic.service.algorithm.PlacementAlgorithmServiceFactory;
import ru.hofftech.parcellogistic.service.command.CommandServiceFactory;
import ru.hofftech.parcellogistic.util.*;

import java.util.Scanner;

@Slf4j
public final class Main {

    public static void main(String[] args) {
        log.info("Start application");
        ConsoleController consoleController = new ConsoleController(
            new Scanner(System.in),
            new CommandServiceFactory(
                    new ParcelLoaderService(
                            new PlacementAlgorithmServiceFactory(),
                            new ParcelsTxtFileParser(new TxtReader()),
                            new JsonWriter()
                    ),
                    new TruckLoaderService(
                            new TrucksJsonFileParser(new JsonReader()),
                            new TxtWriter()
                    )
            )
        );

        consoleController.listen();
    }
}