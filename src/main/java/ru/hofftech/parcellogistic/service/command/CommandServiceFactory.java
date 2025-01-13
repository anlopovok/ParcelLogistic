package ru.hofftech.parcellogistic.service.command;

import ru.hofftech.parcellogistic.enums.CommandType;
import ru.hofftech.parcellogistic.service.ParcelLoaderService;
import ru.hofftech.parcellogistic.service.TruckLoaderService;

import java.util.Scanner;

public class CommandServiceFactory {

    private final ParcelLoaderService parcelLoaderService;

    private final TruckLoaderService truckLoaderService;

    public CommandServiceFactory(
            ParcelLoaderService parcelLoaderService,
            TruckLoaderService truckLoaderService
    ) {
        this.parcelLoaderService = parcelLoaderService;
        this.truckLoaderService = truckLoaderService;
    }

    public CommandService getProcessCommandService(Scanner scanner, CommandType commandType) {

        return switch (commandType) {
            case IMPORT_PARCELS -> new ImportParcelsCommandService(
                    scanner,
                    parcelLoaderService
            );
            case IMPORT_TRUCKS -> new ImportTrucksCommandService(
                    scanner,
                    truckLoaderService
            );
            case EXIT -> new ExitCommandService();
        };
    }
}
