package ru.hofftech.parcellogistic.controller;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.model.PlaceResult;
import ru.hofftech.parcellogistic.service.ParcelLogisticService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class ConsoleController {

    private final Pattern COMMAND_PATTERN = Pattern.compile("import (.+\\.txt) ?(--\\w+)?");

    private final ParcelLogisticService service;

    public ConsoleController(ParcelLogisticService service) {
        this.service = service;
    }

    public void listen() {
        log.info("Start listening...");
        var scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                System.exit(0);
            }

            Matcher matcher = COMMAND_PATTERN.matcher(command);
            if (matcher.matches()) {
                String filePath = matcher.group(1);
                String option = matcher.group(2);

                PlacementAlgorithm algorithm = option != null && java.util.Objects.equals(option, "--optimal")
                        ? PlacementAlgorithm.OPTIMAL
                        : PlacementAlgorithm.SIMPLE;
                log.info("Selected {} algorithm", algorithm);

                try {
                    PlaceResult placeResult = service.placeParcelsFromFile(filePath, algorithm);

                    if (placeResult.isSuccess()) {
                        log.info(
                                """
                                Trucks Placements:
                                Trucks count {}
                                Result:
                                {}
                                """,
                                placeResult.trucksCount(),
                                placeResult
                        );
                    }
                } catch (Throwable e) {
                    log.error("Error: {}", e.getMessage());
                }
            } else {
                log.error("Unknown command: {}", command);
            }
        }
    }
}
