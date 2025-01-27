package ru.hofftech.parcellogistic.model.dto;

import lombok.Getter;
import ru.hofftech.parcellogistic.enums.OutType;
import ru.hofftech.parcellogistic.enums.PlacementAlgorithm;
import ru.hofftech.parcellogistic.exception.IllegalCommandArgumentException;
import ru.hofftech.parcellogistic.model.Size;

import java.util.List;
import java.util.Map;

@Getter
public class LoadCommandOptionsDto {

    private final String filePath;

    private final List<String> parcelNames;

    private final PlacementAlgorithm placementAlgorithm;

    private final List<Size> trucksSizes;

    private final OutType outType;

    private final String outputFileName;

    public enum InputType {
        FILE, TEXT
    }

    public LoadCommandOptionsDto(Map<String, String> options) {
        String parcelsFile = options.get("parcels-file");
        String parcelsText = options.get("parcels-text");
        String trucks = options.get("trucks");
        String type = options.get("type");
        String out = options.get("out");
        String outFileName = options.get("out-filename");

        boolean isFilledParcelsFile = parcelsFile != null && !parcelsFile.isEmpty();
        boolean isFilledParcelNames = parcelsText != null && !parcelsText.isEmpty();

        if (!isFilledParcelsFile && !isFilledParcelNames) {
            throw new IllegalCommandArgumentException("One of values should be passed: parcelsFile or parcelsText");
        }
        if (isFilledParcelsFile && isFilledParcelNames) {
            throw new IllegalCommandArgumentException("Only one of values can be passed: parcelsFile or parcelsText");
        }

        this.filePath = parcelsFile;
        this.parcelNames = isFilledParcelNames ?
                new CommandOptionToParcelNamesDto(parcelsText).getNames()
                : null;
        this.placementAlgorithm = PlacementAlgorithm.getByDescription(type);
        this.trucksSizes = new CommandOptionToSizesDto(trucks).getSizes();
        this.outType = OutType.getByType(out);
        this.outputFileName = outFileName;
    }

    public InputType getInputType() {
        return filePath != null && !filePath.isEmpty() ? InputType.FILE : InputType.TEXT;
    }
}
