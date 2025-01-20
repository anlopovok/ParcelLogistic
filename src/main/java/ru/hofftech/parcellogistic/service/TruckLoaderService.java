package ru.hofftech.parcellogistic.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.model.LoadTrucksResult;
import ru.hofftech.parcellogistic.model.dto.ParcelJsonNodeDto;
import ru.hofftech.parcellogistic.model.dto.TrucksJsonNodeDto;
import ru.hofftech.parcellogistic.util.TrucksJsonFileParser;
import ru.hofftech.parcellogistic.util.TxtWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class TruckLoaderService {

    private static final String OUTPUT_FILE_DIRECTORY = "exports";

    private final TrucksJsonFileParser fileParser;

    private final TxtWriter txtWriter;

    private final String outputFilePath;

    public TruckLoaderService(TrucksJsonFileParser fileParser, TxtWriter txtWriter) {
        this.fileParser = fileParser;
        this.txtWriter = txtWriter;
        String dateFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
        this.outputFilePath = String.format("parcels_loaded_from_trucks_%s.txt", dateFormatted);
    }

    public LoadTrucksResult loadTrucksFromFile(String filePath) {
        TrucksJsonNodeDto trucksJsonNodeDto = fileParser.parseFromFile(filePath);

        log.debug("Start import trucks");
        List<ParcelJsonNodeDto> jsonParcels = trucksJsonNodeDto.getTrucks().stream()
                .flatMap(parcelsJsonNode -> parcelsJsonNode.getParcels().stream())
                .toList();

        LoadTrucksResult loadTrucksResult = new LoadTrucksResult(jsonParcels);
        log.debug("End import trucks");

        txtWriter.save(OUTPUT_FILE_DIRECTORY, outputFilePath, loadTrucksResult.toString());
        log.info("Parcels exported to file: {}/{}", OUTPUT_FILE_DIRECTORY, outputFilePath);

        return loadTrucksResult;
    }
}
