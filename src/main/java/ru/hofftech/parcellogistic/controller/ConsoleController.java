package ru.hofftech.parcellogistic.controller;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.enums.CommandType;
import ru.hofftech.parcellogistic.service.command.CommandServiceFactory;

import java.util.List;
import java.util.Scanner;

@Slf4j
public final class ConsoleController {

    private final Scanner scanner;

    private final CommandServiceFactory commandServiceFactory;

    public ConsoleController(Scanner scanner, CommandServiceFactory commandServiceFactory) {
        this.scanner = scanner;
        this.commandServiceFactory = commandServiceFactory;
    }

    public void listen() {
        log.info("Start listening...");

        List<String> commandVariants = CommandType.getLabels();
        String startMessage = String.format("Enter command [%s]:", String.join("/", commandVariants));

        log.info(startMessage);
        while (scanner.hasNextLine()) {
            try {
                String command = scanner.nextLine();
                CommandType commandType = CommandType.getByUserCommand(command);
                commandServiceFactory.getProcessCommandService(scanner, commandType).processCommand();
            } catch (Exception e) {
                log.error("Error: {}", e.getMessage());
            }
            log.info(startMessage);
        }
    }
}
