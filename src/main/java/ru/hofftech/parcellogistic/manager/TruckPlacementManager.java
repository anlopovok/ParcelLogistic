package ru.hofftech.parcellogistic.manager;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.model.Parcel;
import ru.hofftech.parcellogistic.model.Truck;

@Slf4j
public class TruckPlacementManager {

    public void place(Parcel parcel, Truck truck) {
        for (int line = truck.getHeight() - 1; line >= 0; line--) {
            for (int column = truck.getFilledWidth(line); column < truck.getWidth(); column++) {
                if (canPlaceByStartPosition(parcel, truck, line, column)) {
                    placeByStartPosition(parcel, truck, line, column);
                    truck.add(parcel);
                    return;
                }
            }
        }
    }

    public boolean canPlace(Parcel parcel, Truck truck) {
        for (int line = truck.getHeight() - 1; line >= 0; line--) {
            for (int column = truck.getFilledWidth(line); column < truck.getWidth(); column++) {
                if (canPlaceByStartPosition(parcel, truck, line, column)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canPlaceByStartPosition(
            Parcel parcel,
            Truck truck,
            int truckLine,
            int truckColumn
    ) {
        return isFitsInTruckFromStartPosition(parcel, truck, truckLine, truckColumn)
                && isValidFoundationFromStartPosition(parcel, truck, truckLine, truckColumn);
    }

    private void placeByStartPosition(
            Parcel parcel,
            Truck truck,
            int truckLine,
            int truckColumn
    ) {
        for (int parcelLine = 0; parcelLine < parcel.getHeight(); parcelLine++) {
            for (int parcelColumn = 0; parcelColumn < parcel.getWidthAtLine(parcelLine); parcelColumn++) {
                truck.setCharAtPosition(
                        truckLine + parcelLine,
                        truckColumn + parcelColumn,
                        parcel.getCharAtPosition(parcelLine, parcelColumn)
                );
            }
        }
    }

    private boolean isFitsInTruckFromStartPosition(
            Parcel parcel,
            Truck truck,
            int truckLine,
            int truckColumn
    ) {
        if (parcel.getWidth() > truck.getWidth() - truckColumn) {
            return false;
        }

        for (int parcelLine = 0; parcelLine < parcel.getHeight(); parcelLine++) {
            for (int parcelColumn = 0; parcelColumn < parcel.getWidthAtLine(parcelLine); parcelColumn++) {
                int nextLine = truckLine + parcelLine;
                int nextColumn = truckColumn + parcelColumn;
                if (truck.isOutOfBounds(nextLine, nextColumn)
                        || truck.isFilledAtPosition(nextLine, nextColumn)
                ) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isValidFoundationFromStartPosition(
            Parcel parcel,
            Truck truck,
            int truckLine,
            int truckColumn
    ) {
        int foundationWidth = 0;
        for (int parcelLine = 0; parcelLine < parcel.getHeight(); parcelLine++) {
            for (int parcelColumn = 0; parcelColumn < parcel.getWidthAtLine(parcelLine); parcelColumn++) {
                int lineAfterParcel = truckLine + parcelLine + 1;
                int columnAfterParcel = truckColumn + parcelColumn;
                if (
                    truck.isHeightLessThan(lineAfterParcel)
                    || (
                        !truck.isOutOfBounds(lineAfterParcel, columnAfterParcel)
                        && truck.isFilledAtPosition(lineAfterParcel, columnAfterParcel)
                    )
                    || truckLine + parcel.getHeight() == truck.getHeight()
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
