package ru.hofftech.parcellogistic.service.command;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.service.TruckLoaderService;

import java.util.Scanner;

@Slf4j
public class ImportTrucksCommandService implements CommandService {

    private final Scanner scanner;

    private final TruckLoaderService truckLoaderService;

    public ImportTrucksCommandService(Scanner scanner, TruckLoaderService truckLoaderService) {
        this.scanner = scanner;
        this.truckLoaderService = truckLoaderService;
    }

    public void processCommand() {
        log.info("Enter filePath:");
        String filePath = scanner.nextLine();
        if (filePath.isEmpty()) {
            throw new IllegalArgumentException("Wrong file path");
        }

        truckLoaderService.loadTrucksFromFile(filePath);

        log.info("Trucks imported");
    }
}
