package ru.hofftech.parcellogistic.model.dto;

import lombok.Getter;
import ru.hofftech.parcellogistic.exception.WrongInputFilePathException;
import ru.hofftech.parcellogistic.exception.WrongOutputFilePathException;

import java.util.Map;

@Getter
public class UnloadCommandOptionsDto {

    private final String filePath;

    private final String outputFileName;

    private final boolean withCount;

    public UnloadCommandOptionsDto(Map<String, String> options) {
        String inFile = options.get("infile");
        String outFile = options.get("outfile");

        if (inFile == null || inFile.isEmpty()) {
            throw new WrongInputFilePathException("Empty input filepath");
        }

        if (outFile == null || outFile.isEmpty()) {
            throw new WrongOutputFilePathException("Empty output filePath");
        }

        this.filePath = inFile;
        this.outputFileName = outFile;
        this.withCount = options.containsKey("withcount");
    }
}
