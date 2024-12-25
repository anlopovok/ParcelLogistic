package ru.hofftech.parcellogistic.util;

import ru.hofftech.parcellogistic.model.Parcel;

import java.util.List;

public interface FileParser {
    List<Parcel> parseParcelsFromFile(String filePath);
}
