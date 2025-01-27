package ru.hofftech.parcellogistic.strategy.impl;

import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.strategy.CommandStrategy;

import java.util.Map;

/**
 * Command strategy responsible for terminating the application.
 * This class processes the input command options and performs an immediate exit from the program.
 *
 * This command does not require any specific options. Upon execution, it will call {@link System#exit(int)} with
 * a status code of 0, indicating successful termination of the program.
 */
public class ExitCommand implements CommandStrategy {

    /**
     * Processes the command to terminate the application.
     *
     * This method does not use any options from the provided map. It immediately terminates the program
     * by calling {@link System#exit(int)} with a status code of 0, signaling successful termination.
     *
     * @param options a map of command options (not used in this command)
     * @return {@link OutputResult}
     */
    public OutputResult processCommand(Map<String, String> options) {
        System.exit(0);

        return new OutputResult("exit");
    }
}
