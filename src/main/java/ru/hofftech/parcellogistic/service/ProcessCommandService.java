package ru.hofftech.parcellogistic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.strategy.CommandStrategy;
import ru.hofftech.parcellogistic.strategy.CommandStrategyFactory;
import ru.hofftech.parcellogistic.util.CommandParser;

import java.util.Map;

/**
 * Service for processing commands in the application.
 *
 * This service parses the given command, identifies the corresponding command strategy
 * via the {@link CommandStrategyFactory}, and then processes the command with its options.
 *
 * The process involves the following steps:
 * Trimming the command input.
 * Parsing the command arguments using {@link CommandParser}.
 * Selecting the appropriate command strategy using {@link CommandStrategyFactory}.
 * Executing the command strategy and obtaining the result.
 */
@Slf4j
@AllArgsConstructor
public class ProcessCommandService {

    private final CommandStrategyFactory commandStrategyFactory;

    /**
     * Processes the given command by parsing the arguments and executing the appropriate strategy.
     *
     * First, the command string is trimmed and parsed into arguments. Then, based on the
     * parsed command, the corresponding strategy is selected, and the command is processed.
     * Finally, the result is logged and returned.
     *
     * @param command the command to be processed
     * @return the {@link OutputResult} after processing the command
     * @throws IllegalArgumentException if the command is invalid or cannot be processed
     */
    public OutputResult processCommand(String command) {
        command = command.trim();

        CommandStrategy commandStrategy = commandStrategyFactory.getCommandStrategy(command);
        Map<String, String> commandOptions = CommandParser.parseArguments(command);
        OutputResult outputResult = commandStrategy.processCommand(commandOptions);

        log.info(outputResult.getMessage());

        return outputResult;
    }
}
