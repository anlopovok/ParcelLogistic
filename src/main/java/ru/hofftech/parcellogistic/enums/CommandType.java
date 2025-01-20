package ru.hofftech.parcellogistic.enums;

import java.util.Arrays;
import java.util.List;

public enum CommandType {
    EXIT,
    IMPORT_PARCELS,
    IMPORT_TRUCKS;

    public static List<String> getLabels() {
        return Arrays.stream(CommandType.values())
                .map(CommandType::name)
                .map(String::toLowerCase)
                .toList();
    }

    public static CommandType getByUserCommand(String userCommand) {
        try {
            return CommandType.valueOf(userCommand.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown command: " + userCommand);
        }
    }
}
