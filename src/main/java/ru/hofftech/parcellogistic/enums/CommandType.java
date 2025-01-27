package ru.hofftech.parcellogistic.enums;

public enum CommandType {
    EXIT,
    LOAD,
    UNLOAD,
    FIND,
    CREATE,
    DELETE,
    EDIT;

    public static CommandType getByUserCommand(String userCommand) {
        try {
            return CommandType.valueOf(userCommand.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown command: " + userCommand);
        }
    }
}
