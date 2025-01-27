package ru.hofftech.parcellogistic.strategy.impl;

import lombok.AllArgsConstructor;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.model.dto.CommandOptionsToParcelEntityDto;
import ru.hofftech.parcellogistic.model.entity.ParcelEntity;
import ru.hofftech.parcellogistic.service.ParcelService;
import ru.hofftech.parcellogistic.strategy.CommandStrategy;

import java.util.Map;

/**
 * Command strategy responsible for editing an existing {@link ParcelEntity}.
 * This class processes the input command options, converts them into a {@link ParcelEntity},
 * and delegates the update of the parcel to the {@link ParcelService}.
 *
 * The options map typically contains the parameters needed for editing the parcel, such as the parcel's
 * ID, name, form, and symbol.
 */
@AllArgsConstructor
public class EditCommand implements CommandStrategy {

    private final ParcelService parcelService;

    /**
     * Processes the command to edit an existing parcel.
     *
     * This method extracts the necessary options from the input map (id, name, form, symbol), converts them into a
     * {@link ParcelEntity} using the {@link CommandOptionsToParcelEntityDto}, and then delegates the update of the
     * parcel to the {@link ParcelService}. Once the parcel is updated, a log entry is generated containing the
     * parcel's name or ID, and its content.
     *
     * @param options a map containing the command options, such as id, name, form, and symbol, for the parcel
     * @return {@link OutputResult}
     */
    public OutputResult processCommand(Map<String, String> options) {
        String id = options.get("id");
        String name = options.get("name");
        String form = options.get("form");
        String symbol = options.get("symbol");

        ParcelEntity parcel = new CommandOptionsToParcelEntityDto(name, form, symbol).toParcelEntity();
        ParcelEntity updatedParcel = parcelService.updateParcel(id, parcel);

        return new OutputResult(
                String.format("""
                        id(name): %s
                        form:
                        %s""",
                        updatedParcel.name(),
                        String.join(String.format("%n"), parcel.content())
                )
        );
    }
}
