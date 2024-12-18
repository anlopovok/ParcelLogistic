package ru.hofftech.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.enums.PlacementAlgorithm;
import ru.hofftech.model.PlaceResult;
import ru.hofftech.service.ParcelLogisticService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public final class ConsoleController {

    private ParcelLogisticService service;

    public ConsoleController(ParcelLogisticService service) {
        this.service = service;
    }

    private final Pattern COMMAND_PATTERN = Pattern.compile("import (.+\\.txt) ?(--\\w+)?");

    public void listen() {
        log.info("Start listening...");
        var scanner = new Scanner(System.in);

        while(scanner.hasNextLine()){
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                System.exit(0);
            }

            Matcher matcher = COMMAND_PATTERN.matcher(command);
            if (matcher.matches()) {
                String filePath = matcher.group(1);
                String option = matcher.group(2);

                PlacementAlgorithm algorithm = option != null && option.equals("--optimal")
                        ? PlacementAlgorithm.OPTIMAL
                        : PlacementAlgorithm.SIMPLE;
                log.info("Selected {} algorithm", algorithm.toString());

                PlaceResult placeResult = service.placeParcelsFromFile(filePath, algorithm);
                if (placeResult.isSuccess()) {
                    log.info(
                        "Trucks Placements:\nTrucks count {}\nResult:\n{}",
                        placeResult.trucksCount(),
                        placeResult.toString()
                    );
                }
            } else {
                log.error("Unknown command: {}", command);
            }
        }
    }
}
