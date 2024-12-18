package ru.hofftech.util;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.model.Parcel;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TxtParser implements FileParser {

    private final TxtReader txtReader;

    public TxtParser(TxtReader txtReader) {
        this.txtReader = txtReader;
    }

    public List<Parcel> parseParcelsFromFile(String filePath) {
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

        log.info("Parsed parcels count: {}", parcels.size());

        return parcels;
    }
}
