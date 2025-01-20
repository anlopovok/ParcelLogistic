package ru.hofftech.parcellogistic.service.command;

public class ExitCommandService implements CommandService {

    public void processCommand() {
        System.exit(0);
    }
}
