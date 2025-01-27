package ru.hofftech.parcellogistic.strategy.impl;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.model.UnloadTrucksResult;
import ru.hofftech.parcellogistic.model.dto.UnloadCommandOptionsDto;
import ru.hofftech.parcellogistic.service.OutputUnloadedTrucksService;
import ru.hofftech.parcellogistic.service.UnloadTrucksService;
import ru.hofftech.parcellogistic.strategy.CommandStrategy;

import java.util.Map;

/**
 * Command strategy responsible for unloading trucks and outputting the result.
 * This class processes the input command options, unloads the trucks from a specified file using the {@link UnloadTrucksService},
 * and outputs the result using the {@link OutputUnloadedTrucksService}.
 *
 * The command expects parameters for the input file path, output file path, and an optional "withcount" flag
 * to include the count of items in the output.
 */
@AllArgsConstructor
public class UnloadCommand implements CommandStrategy {

    private final UnloadTrucksService unloadTrucksService;

    private final OutputUnloadedTrucksService outputUnloadedTrucksService;

    /**
     * Processes the command to unload trucks and output the result.
     * 
     * This method extracts the necessary options from the provided map, including the input file path,
     * output file path, and an optional "withcount" flag.
     *
     * @param options a map containing the command options with the following keys:
     *                - "infile" the path to the input file containing the trucks data
     *                - "outfile" the path to the output file where the result will be saved
     *                - "withcount" (optional) a flag indicating whether to include item count in the output
     * @return {@link OutputResult}
     */
    public OutputResult processCommand(Map<String, String> options) {
        UnloadCommandOptionsDto optionsDto = new UnloadCommandOptionsDto(options);

        UnloadTrucksResult unloadTrucksResult = unloadTrucksService.unloadTrucksFromFile(optionsDto.getFilePath());

        return outputUnloadedTrucksService.out(
                unloadTrucksResult,
                optionsDto.getOutputFileName(),
                optionsDto.isWithCount()
        );
    }
}
