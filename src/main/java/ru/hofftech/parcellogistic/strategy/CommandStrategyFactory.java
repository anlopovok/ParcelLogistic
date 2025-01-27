package ru.hofftech.parcellogistic.strategy;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.enums.CommandType;
import ru.hofftech.parcellogistic.service.OutputLoadedTrucksService;
import ru.hofftech.parcellogistic.service.OutputUnloadedTrucksService;
import ru.hofftech.parcellogistic.service.ParcelService;
import ru.hofftech.parcellogistic.service.UnloadTrucksService;
import ru.hofftech.parcellogistic.service.LoadTruckFromFileService;
import ru.hofftech.parcellogistic.service.LoadTruckFromTextService;
import ru.hofftech.parcellogistic.strategy.impl.CreateCommand;
import ru.hofftech.parcellogistic.strategy.impl.DeleteCommand;
import ru.hofftech.parcellogistic.strategy.impl.EditCommand;
import ru.hofftech.parcellogistic.strategy.impl.ExitCommand;
import ru.hofftech.parcellogistic.strategy.impl.FindCommand;
import ru.hofftech.parcellogistic.strategy.impl.LoadCommand;
import ru.hofftech.parcellogistic.strategy.impl.UnloadCommand;
import ru.hofftech.parcellogistic.util.CommandParser;

@AllArgsConstructor
public class CommandStrategyFactory {

    private final LoadTruckFromFileService loadTruckFromFileService;

    private final LoadTruckFromTextService loadTruckFromTextService;

    private final CommandParser commandParser;

    private final OutputLoadedTrucksService outputLoadedTrucksService;

    private final UnloadTrucksService unloadTrucksService;

    private final OutputUnloadedTrucksService outputUnloadedTrucksService;

    private final ParcelService parcelService;

    public CommandStrategy getCommandStrategy(String command) {
        CommandType commandType = commandParser.parseCommand(command);

        return switch (commandType) {
            case LOAD -> new LoadCommand(loadTruckFromFileService, loadTruckFromTextService, outputLoadedTrucksService);
            case UNLOAD -> new UnloadCommand(unloadTrucksService, outputUnloadedTrucksService);
            case FIND -> new FindCommand(parcelService);
            case CREATE -> new CreateCommand(parcelService);
            case EDIT -> new EditCommand(parcelService);
            case DELETE -> new DeleteCommand(parcelService);
            case EXIT -> new ExitCommand();
        };
    }
}
