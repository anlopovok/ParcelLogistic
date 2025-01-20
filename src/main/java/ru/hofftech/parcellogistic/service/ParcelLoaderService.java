package ru.hofftech.parcellogistic.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.LoadParcelsResult;
import ru.hofftech.parcellogistic.service.algorithm.PlacementAlgorithmServiceFactory;
import ru.hofftech.parcellogistic.settings.TruckSettings;
import ru.hofftech.parcellogistic.util.JsonWriter;
import ru.hofftech.parcellogistic.util.ParcelsTxtFileParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class ParcelLoaderService {

    private static final String OUTPUT_FILE_DIRECTORY = "exports";

    private static final int TRUCK_WIDTH = 6;

    private static final int TRUCK_HEIGHT = 6;

    private final PlacementAlgorithmServiceFactory placementAlgorithmServiceFactory;

    private final ParcelsTxtFileParser fileParser;

    private final JsonWriter jsonWriter;

    private final String outputFilePath;

    public ParcelLoaderService(
            PlacementAlgorithmServiceFactory placementAlgorithmServiceFactory,
            ParcelsTxtFileParser parcelsTxtFileParser,
            JsonWriter jsonWriter
    ) {
        this.placementAlgorithmServiceFactory = placementAlgorithmServiceFactory;
        this.fileParser = parcelsTxtFileParser;
        this.jsonWriter = jsonWriter;
        String dateFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
        this.outputFilePath = String.format("trucks_with_placed_parcels_%s.json", dateFormatted);
    }

    @SneakyThrows
    public LoadParcelsResult loadParcelsFromFile(
            String filePath,
            PlacementAlgorithm placementAlgorithm,
            int trucksCount,
            boolean isSaveToFile
    ) {
        List<Parcel> parcels = fileParser.parseFromFile(filePath);
        List<TruckSettings> trucks = new ArrayList<>();
        for (int i = 0; i < trucksCount; i++) {
            trucks.add(new TruckSettings(TRUCK_WIDTH, TRUCK_HEIGHT));
        }

        log.debug("Start load parcels");
        LoadParcelsResult loadParcelsResult = placementAlgorithmServiceFactory
                .getPlacementAlgorithmService(placementAlgorithm)
                .placeParcelsToTrucks(parcels, trucks);
        log.debug("End load parcels");

        if (isSaveToFile) {
            jsonWriter.save(OUTPUT_FILE_DIRECTORY, outputFilePath, loadParcelsResult.toJson());
            log.info("Parcels saved to file: {}/{}", OUTPUT_FILE_DIRECTORY, outputFilePath);
        }

        return loadParcelsResult;
    }
}
