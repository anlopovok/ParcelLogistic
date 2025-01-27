package ru.hofftech.parcellogistic.strategy.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.exception.IllegalCommandArgumentException;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;
import ru.hofftech.parcellogistic.service.ParcelService;
import ru.hofftech.parcellogistic.strategy.CommandStrategy;

import java.util.Map;

/**
 * Command strategy responsible for finding a {@link ParcelEntity} by its name.
 * This class processes the input command options, validates the presence of the parcel name,
 * and retrieves the corresponding parcel from the {@link ParcelService}.
 *
 * If the provided name is empty or null, an {@link IllegalCommandArgumentException} is thrown.
 */
@Slf4j
@AllArgsConstructor
public class FindCommand implements CommandStrategy {

    private final ParcelService parcelService;

    /**
     * Processes the command to find a parcel by its name.
     *
     * This method checks if the "name" parameter is present in the options map. If it is empty or null,
     * an {@link IllegalCommandArgumentException} is thrown. If valid, the parcel is retrieved from
     * the {@link ParcelService}. After retrieving the parcel, a log entry is generated containing the
     * parcel's name or ID, and its content.
     *
     * @param options a map containing the command options, where the key for the parcel name should be provided
     * @return {@link OutputResult}
     * @throws IllegalCommandArgumentException if the parcel name is not provided or is empty
     */
    public OutputResult processCommand(Map<String, String> options) {
        String name = options.get("");
        if (name == null || name.isEmpty()) {
            throw new IllegalCommandArgumentException("Name is empty");
        }

        ParcelEntity parcel = parcelService.getParcelByName(name);

        return new OutputResult(
                String.format("""
                        id(name): %s
                        form:
                        %s""",
                        parcel.name(),
                        String.join(String.format("%n"), parcel.content())
                )
        );
    }
}