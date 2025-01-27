package ru.hofftech.parcellogistic.model.dto;

import lombok.Getter;
import ru.hofftech.parcellogistic.exception.IllegalCommandArgumentException;
import ru.hofftech.parcellogistic.exception.WrongTruckSettingsFormatException;
import ru.hofftech.parcellogistic.model.Size;

import java.util.Arrays;
import java.util.List;

@Getter
public class CommandOptionToSizesDto {

    private static final String NEW_LINE_AS_STRING = "\\n";

    private static final String NEW_LINE = "\n";

    private static final String DELIMITER_PATTERN = "\\dx\\d";

    private static final String DELIMITER = "x";

    private final List<Size> sizes;

    public CommandOptionToSizesDto(String formattedString) {
        String[] lines = formattedString.replace(NEW_LINE_AS_STRING, NEW_LINE).split(NEW_LINE);
        if (lines.length == 0) {
            throw new IllegalCommandArgumentException("Trucks count must be more than 0");
        }

        sizes = Arrays.stream(lines).map(this::createSize).toList();
    }

    private Size createSize(String formattedString) {
        if (!formattedString.matches(DELIMITER_PATTERN)) {
            throw new WrongTruckSettingsFormatException(
                    "Wrong truck size format. Format should be like 3x3"
            );
        }

        String[] parts = formattedString.split(DELIMITER);

        return new Size(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
