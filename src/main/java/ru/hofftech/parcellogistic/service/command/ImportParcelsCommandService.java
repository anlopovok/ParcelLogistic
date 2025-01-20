package ru.hofftech.parcellogistic.service.command;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.model.LoadParcelsResult;
import ru.hofftech.parcellogistic.service.ParcelLoaderService;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class ImportParcelsCommandService implements CommandService {

    private final Scanner scanner;

    private final ParcelLoaderService parcelLoaderService;

    public ImportParcelsCommandService(
            Scanner scanner,
            ParcelLoaderService parcelLoaderService
    ) {
        this.scanner = scanner;
        this.parcelLoaderService = parcelLoaderService;
    }

    public void processCommand() {
        LoadParcelsResult loadParcelsResult = parcelLoaderService.loadParcelsFromFile(
                getFilePath(),
                getPlacementAlgorithm(),
                getTrucksCount(),
                isSaveToFile()
        );

        if (loadParcelsResult.isSuccess()) {
            log.info(
                    """
                            Trucks Placements:
                            Trucks count {}
                            Result:
                            {}
                            """,
                    loadParcelsResult.trucksCount(),
                    loadParcelsResult
            );
        }
    }

    private String getFilePath() {
        log.info("Enter filePath:");
        String filePath = scanner.nextLine();
        if (filePath.isEmpty()) {
            throw new IllegalArgumentException("Wrong file path");
        }

        return filePath;
    }

    private PlacementAlgorithm getPlacementAlgorithm() {
        List<String> algorithmVariants = PlacementAlgorithm.getLabels();
        String commandLabel = String.format("Enter algorithm [%s]:", String.join("/", algorithmVariants));

        log.info(commandLabel);
        String algorithm = scanner.nextLine().toUpperCase();

        return PlacementAlgorithm.valueOf(algorithm);
    }

    private int getTrucksCount() {
        log.info("Enter trucksCount:");
        int trucksCount = scanner.nextInt();
        scanner.nextLine();
        if (trucksCount < 1) {
            throw new IllegalArgumentException("Trucks count must be more than 0");
        }

        return trucksCount;
    }

    private boolean isSaveToFile() {
        log.info("Save result to file [y/n] (Skip to choose n):");
        String saveResultOption = scanner.nextLine();

        return "y".equalsIgnoreCase(saveResultOption);
    }
}
