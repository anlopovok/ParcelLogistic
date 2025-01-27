package ru.hofftech.parcellogistic.strategy.impl;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.model.dto.CommandOptionsToParcelEntityDto;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;
import ru.hofftech.parcellogistic.service.ParcelService;
import ru.hofftech.parcellogistic.strategy.CommandStrategy;

import java.util.Map;

/**
 * Command strategy responsible for creating a new {@link ParcelEntity} based on the provided command options.
 * This class processes the input command options, converts them into a {@link ParcelEntity},
 * and delegates the creation of the parcel to the {@link ParcelService}.
 *
 * The options map typically contains the parameters needed for the creation of the parcel, such as the parcel's
 * name, form, and symbol.
 */
@AllArgsConstructor
public class CreateCommand implements CommandStrategy {

    private final ParcelService parcelService;

    /**
     * Processes the command to create a new parcel.
     *
     * This method extracts the necessary options from the input map (name, form, symbol), converts them into a
     * {@link ParcelEntity} using the {@link CommandOptionsToParcelEntityDto}, and then delegates the creation of the
     * parcel to the {@link ParcelService}. Once the parcel is created, a log entry is generated containing the
     * parcel's name or ID, and its content.
     *
     * @param options a map containing the command options, such as name, form, and symbol, for the parcel
     * @return {@link OutputResult}
     */
    public OutputResult processCommand(Map<String, String> options) {
        String name = options.get("name");
        String form = options.get("form");
        String symbol = options.get("symbol");

        ParcelEntity parcel = new CommandOptionsToParcelEntityDto(name, form, symbol).toParcelEntity();
        ParcelEntity createdParcel = parcelService.createParcel(parcel);

        return new OutputResult(
                String.format(
                        """
                        id(name): %s
                        form:
                        %s""",
                        createdParcel.name(),
                        String.join(String.format("%n"), parcel.content())
                )
        );
    }
}
