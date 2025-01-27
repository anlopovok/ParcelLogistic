package ru.hofftech.parcellogistic.util;

import ru.hofftech.parcellogistic.enums.CommandType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing commands and their arguments.
 * 
 * This class provides methods for parsing a command string to extract the command type and its associated arguments.
 * It uses regular expressions to match the command and its arguments, making it suitable for processing user inputs
 * from a command-line interface.
 */
public class CommandParser {

    private static final String COMMAND_PATTERN = "(?<command>\\w+)( .+)*";

    private static final String ONE_ARGUMENT_PATTERN = "[“\\\"](?<value>[^“”\\\"]+)[”\\\"]";

    private static final String ARGUMENTS_PATTERN = "-(?<argument>[\\w-]+)\\s+[“\"](?<value>[^“”\"]+)[”\"]|--(?<flag>[\\w-]+)";

    /**
     * Parses the arguments and their associated values from the full command string.
     * 
     * This method uses regular expressions to match argument-value pairs and stores them
     * in a map. The map uses the argument name as the key and the argument value as the value.
     *
     * @param fullCommand the full command string, including the command and arguments
     * @return Map<String, String> a map containing the arguments and their associated values
     */
    public static Map<String, String> parseArguments(String fullCommand) {
        Map<String, String> options = new HashMap<>();
        Pattern argumentsPattern = Pattern.compile(ARGUMENTS_PATTERN);
        Matcher matcher = argumentsPattern.matcher(fullCommand);
        while (matcher.find()) {
            if (matcher.group("argument") != null && matcher.group("value") != null) {
                options.put(matcher.group("argument"), matcher.group("value"));
            } else if (matcher.group("flag") != null) {
                options.put(matcher.group("flag"), "");

            }
        }
        if (!options.isEmpty()) {
            return options;
        }

        Pattern oneArgumentPattern = Pattern.compile(ONE_ARGUMENT_PATTERN);
        matcher = oneArgumentPattern.matcher(fullCommand);
        if (matcher.find()) {
            options.put("", matcher.group("value"));
        }

        return options;
    }

    /**
     * Parses the command part of the full command string.
     * 
     * This method extracts the command name from the provided command string.
     * It identifies the command by matching the first word before any arguments.
     *
     * @param fullCommand the full command string, including the command and arguments
     * @return CommandType the corresponding {@link CommandType} for the parsed command
     */
    public CommandType parseCommand(String fullCommand) {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        Matcher matcher = pattern.matcher(fullCommand);
        String command = "";
        if (matcher.find()) {
            command = matcher.group("command");
        }

        return CommandType.getByUserCommand(command);
    }
}
