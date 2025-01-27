package ru.hofftech.parcellogistic.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.service.ProcessCommandService;

import java.util.Scanner;

/**
 * Controller class responsible for handling console input commands and processing them
 * using the appropriate command strategy.
 */
@Slf4j
@AllArgsConstructor
public final class ConsoleController {

    private final Scanner scanner;

    private final ProcessCommandService processCommandService;

    /**
     * Listens for user input commands and processes them accordingly.
     *
     * The method continuously reads commands from the console, parses them,
     * determines the appropriate command strategy, and executes the command.
     * In case of an error, it logs the exception message.
     */
    public void listen() {
        log.info("Start listening console commands...");

        log.info("Enter command:");
        while (scanner.hasNextLine()) {
            try {
                String command = scanner.nextLine();
                OutputResult outputResult = processCommandService.processCommand(command);

                log.info(outputResult.getMessage());
            } catch (Exception e) {
                log.error("Error: {}", e.getMessage());
            }
            log.info("Enter command:");
        }
    }
}
