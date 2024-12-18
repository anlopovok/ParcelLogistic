package ru.hofftech.util;

import ru.hofftech.model.Parcel;

import java.util.List;

public interface FileParser {
    public List<Parcel> parseParcelsFromFile(String filePath);
}
