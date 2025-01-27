package ru.hofftech.parcellogistic.strategy.impl;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.exception.IllegalCommandArgumentException;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;
import ru.hofftech.parcellogistic.service.ParcelService;
import ru.hofftech.parcellogistic.strategy.CommandStrategy;

import java.util.Map;

/**
 * Command strategy responsible for deleting a {@link ParcelEntity} by its name.
 * This class processes the input command options, validates the presence of the parcel name,
 * and delegates the deletion of the parcel to the {@link ParcelService}.
 *
 * If the provided name is empty or null, an {@link IllegalCommandArgumentException} is thrown.
 */
@AllArgsConstructor
public class DeleteCommand implements CommandStrategy {

    private final ParcelService parcelService;

    /**
     * Processes the command to delete a parcel by its name.
     *
     * This method checks if the "name" parameter is present in the options map. If it is empty or null,
     * an {@link IllegalCommandArgumentException} is thrown. If valid, the parcel deletion is delegated to
     * the {@link ParcelService}. After deletion, a log entry is generated indicating the deletion.
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
        parcelService.deleteParcel(name);

        return new OutputResult(
                String.format("Посылка \"%s\" удалена", name)
        );
    }
}
