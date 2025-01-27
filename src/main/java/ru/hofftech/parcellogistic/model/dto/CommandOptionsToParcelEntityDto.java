package ru.hofftech.parcellogistic.model.dto;

import ru.hofftech.parcellogistic.exception.IllegalCommandArgumentException;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;

import java.util.Arrays;
import java.util.List;

public record CommandOptionsToParcelEntityDto(String name, String form, String symbol) {

    private static final String NEW_LINE_AS_STRING = "\\n";

    private static final String NEW_LINE = "\n";

    private static final String SPACE_PATTERN = "[^ ]";

    public CommandOptionsToParcelEntityDto {
        if (name == null || name.isEmpty()) {
            throw new IllegalCommandArgumentException("Name is empty");
        }
        if (form == null || form.isEmpty()) {
            throw new IllegalCommandArgumentException("Form is empty");
        }
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalCommandArgumentException("Symbol is empty");
        } else if (symbol.length() > 1) {
            throw new IllegalCommandArgumentException("String for symbol is too long");
        }
    }

    public ParcelEntity toParcelEntity() {
        String[] formLines = form.replace(NEW_LINE_AS_STRING, NEW_LINE).split(NEW_LINE);
        List<String> content = Arrays.stream(formLines)
                .map(line -> line.replaceAll(SPACE_PATTERN, symbol))
                .toList();

        return ParcelEntity.builder()
                .name(name)
                .content(content)
                .build();
    }
}
