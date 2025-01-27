package ru.hofftech.parcellogistic.service;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.model.UnloadTrucksResult;
import ru.hofftech.parcellogistic.util.CsvWriter;

/**
 * Service for outputting the results of unloading trucks.
 */
@AllArgsConstructor
public class OutputUnloadedTrucksService {

    private static final String DELIMITER = ";";

    private final CsvWriter csvWriter;

    /**
     * Outputs the result of truck unloading to a CSV file.
     *
     * @param unloadTrucksResult the result of the truck unloading process
     * @param outputFileName the name of the output file
     * @param withCount flag indicating whether to include parcel count in the output
     * @return {@link OutputResult}
     */
    public OutputResult out(UnloadTrucksResult unloadTrucksResult, String outputFileName, boolean withCount) {
        csvWriter.save(outputFileName, unloadTrucksResult.toCsv(withCount), DELIMITER);

        return new OutputResult(
                String.format("""
                                %s
                                %s
                                """,
                        outputFileName,
                        unloadTrucksResult.getAsString(withCount)
                )
        );
    }
}
