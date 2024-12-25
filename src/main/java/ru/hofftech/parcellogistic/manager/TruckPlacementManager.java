package ru.hofftech.parcellogistic.manager;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.TruckPlacement;

@Slf4j
public class TruckPlacementManager {

    public void place(Parcel parcel, TruckPlacement truckPlacement) {
        for (int line = truckPlacement.getHeight() - 1; line >= 0; line--) {
            for (int column = truckPlacement.getFilledWidth(line); column < truckPlacement.getWidth(); column++) {
                if (canPlaceByStartPosition(parcel, truckPlacement, line, column)) {
                    placeByStartPosition(parcel, truckPlacement, line, column);
                    return;
                }
            }
        }
    }

    public boolean canPlace(Parcel parcel, TruckPlacement truckPlacement) {
        for (int line = truckPlacement.getHeight() - 1; line >= 0; line--) {
            for (int column = truckPlacement.getFilledWidth(line); column < truckPlacement.getWidth(); column++) {
                if (canPlaceByStartPosition(parcel, truckPlacement, line, column)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canPlaceByStartPosition(
            Parcel parcel,
            TruckPlacement truckPlacement,
            int truckLine,
            int truckColumn
    ) {
        return isFitsInTruckFromStartPosition(parcel, truckPlacement, truckLine, truckColumn)
                && isValidFoundationFromStartPosition(parcel, truckPlacement, truckLine, truckColumn);
    }

    private void placeByStartPosition(
            Parcel parcel,
            TruckPlacement truckPlacement,
            int truckLine,
            int truckColumn
    ) {
        for (int parcelLine = 0; parcelLine < parcel.getHeight(); parcelLine++) {
            for (int parcelColumn = 0; parcelColumn < parcel.getWidthAtLine(parcelLine); parcelColumn++) {
                truckPlacement.setCharAtPosition(
                        truckLine + parcelLine,
                        truckColumn + parcelColumn,
                        parcel.getCharAtPosition(parcelLine, parcelColumn)
                );
            }
        }
    }

    private boolean isFitsInTruckFromStartPosition(
            Parcel parcel,
            TruckPlacement truckPlacement,
            int truckLine,
            int truckColumn
    ) {
        if (parcel.getWidth() > truckPlacement.getWidth() - truckColumn) {
            return false;
        }

        for (int parcelLine = 0; parcelLine < parcel.getHeight(); parcelLine++) {
            for (int parcelColumn = 0; parcelColumn < parcel.getWidthAtLine(parcelLine); parcelColumn++) {
                int nextLine = truckLine + parcelLine;
                int nextColumn = truckColumn + parcelColumn;
                if (truckPlacement.isOutOfBounds(nextLine, nextColumn)
                        || truckPlacement.isFilledAtPosition(nextLine, nextColumn)
                ) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isValidFoundationFromStartPosition(
            Parcel parcel,
            TruckPlacement truckPlacement,
            int truckLine,
            int truckColumn
    ) {
        int foundationWidth = 0;
        for (int parcelLine = 0; parcelLine < parcel.getHeight(); parcelLine++) {
            for (int parcelColumn = 0; parcelColumn < parcel.getWidthAtLine(parcelLine); parcelColumn++) {
                int lineAfterParcel = truckLine + parcelLine + 1;
                int columnAfterParcel = truckColumn + parcelColumn;
                if (
                    truckPlacement.isHeightLessThan(lineAfterParcel)
                    || (
                        !truckPlacement.isOutOfBounds(lineAfterParcel, columnAfterParcel)
                        && truckPlacement.isFilledAtPosition(lineAfterParcel, columnAfterParcel)
                    )
                    || truckLine + parcel.getHeight() == truckPlacement.getHeight()
                ) {
                    foundationWidth++;
                }
            }

            if (foundationWidth >= Math.ceil(parcel.getWidthAtLine(parcelLine) / 2.0)) {
                return true;
            }
        }

        return false;
    }
}
