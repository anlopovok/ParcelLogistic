package ru.hofftech.parcellogistic.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.model.Parcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ParcelsTxtFileParser {

    private final TxtReader txtReader;

    public ParcelsTxtFileParser(TxtReader txtReader) {
        this.txtReader = txtReader;
    }

    @SneakyThrows
    public List<Parcel> parseFromFile(String filePath) {
        if (!filePath.matches(".+\\.txt")) {
            throw new IllegalArgumentException("File extension should be txt");
        }

        try {
            log.debug("Start parsing parcels from file {}", filePath);

            List<Parcel> parcels = new ArrayList<>();

            List<String> currentParcel = new ArrayList<>();
            txtReader.readAllLines(filePath).forEach(line -> {
                if (line.isEmpty()) {
                    parcels.add(new Parcel(new ArrayList<>(currentParcel)));
                    currentParcel.clear();
                } else {
                    currentParcel.add(line);
                }
            });

            if (!currentParcel.isEmpty()) {
                parcels.add(new Parcel(currentParcel));
            }

            log.debug("Parsed parcels count: {}", parcels.size());

            return parcels;
        } catch (Exception e) {
            throw new IOException("Error while parsing file " + filePath);
        }
    }
}
