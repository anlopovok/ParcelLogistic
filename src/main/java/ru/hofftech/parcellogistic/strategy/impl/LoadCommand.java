package ru.hofftech.parcellogistic.strategy.impl;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.dto.LoadCommandOptionsDto;
import ru.hofftech.parcellogistic.service.LoadTrucksService;
import ru.hofftech.parcellogistic.service.OutputLoadedTrucksService;
import ru.hofftech.parcellogistic.service.LoadTruckFromFileService;
import ru.hofftech.parcellogistic.service.LoadTruckFromTextService;
import ru.hofftech.parcellogistic.strategy.CommandStrategy;

import java.util.Map;

/**
 * Command strategy responsible for loading parcels into trucks and outputting the result.
 * This class processes the input command options, loads the trucks from a specified file using the {@link LoadTrucksService},
 * and outputs the result using the {@link OutputLoadedTrucksService}.
 *
 * The command expects several parameters, including the file path for the parcels, truck settings, the type of placement algorithm,
 * and output preferences such as output format and file name.
 */
@AllArgsConstructor
public class LoadCommand implements CommandStrategy {

    private final LoadTruckFromFileService loadTruckFromFileService;

    private final LoadTruckFromTextService loadTruckFromTextService;

    private final OutputLoadedTrucksService outputLoadedTrucksService;

    /**
     * Processes the command to load trucks and output the result.
     * 
     * This method extracts the necessary options from the provided map, including the file path for the parcels file,
     * truck settings, the type of placement algorithm to use, the output format type, and the output file name.
     *
     * @param options a map containing the command options with the following keys:
     *                - "parcels-file" the path to the parcels file
     *                - "trucks" the truck settings
     *                - "type" the type of placement algorithm
     *                - "out" the output type
     *                - "out-filename" the output file name
     * @return {@link OutputResult}
     */
    public OutputResult processCommand(Map<String, String> options) {
        LoadCommandOptionsDto optionsDto = new LoadCommandOptionsDto(options);

        LoadTrucksResult loadTrucksResult = switch (optionsDto.getInputType()) {
            case FILE -> loadTruckFromFileService.loadTrucks(
                    optionsDto.getFilePath(),
                    optionsDto.getPlacementAlgorithm(),
                    optionsDto.getTrucksSizes()
            );
            case TEXT -> loadTruckFromTextService.loadTrucks(
                    optionsDto.getParcelNames(),
                    optionsDto.getPlacementAlgorithm(),
                    optionsDto.getTrucksSizes()
            );
        };

        return outputLoadedTrucksService.out(loadTrucksResult, optionsDto.getOutType(), optionsDto.getOutputFileName());
    }
}
