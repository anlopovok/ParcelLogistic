package ru.hofftech.parcellogistic.service;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.enums.OutType;
import ru.hofftech.parcellogistic.exception.WrongOutputFilePathException;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.util.JsonWriter;

/**
 * Service for outputting the results of loading trucks.
 */
@AllArgsConstructor
public class OutputLoadedTrucksService {

    private final JsonWriter jsonWriter;

    /**
     * Outputs the result of truck loading based on the specified output type.
     *
     * @param loadTrucksResult the result of the truck loading process
     * @param outType the type of output (TEXT or JSON file)
     * @param outputFileName the name of the output file (if applicable)
     * @return {@link OutputResult}
     * @throws WrongOutputFilePathException if the output file path is invalid
     */
    public OutputResult out(LoadTrucksResult loadTrucksResult, OutType outType, String outputFileName) {
        if (outType.isFile() && (outputFileName == null || outputFileName.isEmpty())) {
            throw new WrongOutputFilePathException("Empty output filePath");
        }

        return switch (outType) {
            case JSON -> outJson(loadTrucksResult, outputFileName);
            case TEXT -> new OutputResult(loadTrucksResult.getAsString());
        };
    }

    private OutputResult outJson(LoadTrucksResult loadTrucksResult, String outputFileName) {
        jsonWriter.save(outputFileName, loadTrucksResult.getAsJson());

        return new OutputResult(
                String.format("""
                                %s
                                %s
                                """,
                        outputFileName,
                        loadTrucksResult.getJsonAsString()
                )
        );
    }
}
