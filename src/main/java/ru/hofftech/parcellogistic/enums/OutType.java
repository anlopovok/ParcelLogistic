package ru.hofftech.parcellogistic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutType {
    JSON("json-file"),
    TEXT("text");

    private final String type;

    public static OutType getByType(String type) {
        for (OutType fileType : values()) {
            if (fileType.type.equals(type)) {
                return fileType;
            }
        }
        throw new IllegalArgumentException("Unknown out type: " + type);
    }

    public boolean isFile() {
        return this == JSON;
    }
}
