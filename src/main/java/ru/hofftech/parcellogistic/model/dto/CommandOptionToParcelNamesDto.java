package ru.hofftech.parcellogistic.model.dto;

import lombok.Getter;
import ru.hofftech.parcellogistic.exception.IllegalCommandArgumentException;

import java.util.Arrays;
import java.util.List;

@Getter
public class CommandOptionToParcelNamesDto {

    private static final String NEW_LINE_AS_STRING = "\\n";

    private static final String NEW_LINE = "\n";

    private final List<String> names;

    public CommandOptionToParcelNamesDto(String formattedString) {
        String[] lines = formattedString.replace(NEW_LINE_AS_STRING, NEW_LINE).split(NEW_LINE);
        if (lines.length == 0) {
            throw new IllegalCommandArgumentException("Parcels count must be more than 0");
        }

        this.names = Arrays.stream(lines).toList();
    }
}
