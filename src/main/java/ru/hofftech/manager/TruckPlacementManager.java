package ru.hofftech.manager;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.exception.OutOfTruckPlacementSize;
import ru.hofftech.model.Parcel;
import ru.hofftech.model.TruckPlacement;

@Slf4j
public class TruckPlacementManager {

    private final Parcel parcel;

    private final TruckPlacement truckPlacement;

    public TruckPlacementManager(Parcel parcel, TruckPlacement truckPlacement) {
        this.parcel = parcel;
        this.truckPlacement = truckPlacement;
    }

    public void place() throws OutOfTruckPlacementSize {
        for (int line = truckPlacement.getHeight() - 1; line >= 0; line--) {
            for (int column = truckPlacement.getFilledWidth(line); column < truckPlacement.getWidth(); column++) {
                if (canPlaceTo(line, column)) {
                    placeTo(line, column);
                    return;
                }
            }
        }

        log.info("Cannot place parcel to truck");
        throw new OutOfTruckPlacementSize("Cannot place parcel to truck");
    }

    private boolean canPlaceTo(int truckLine, int truckColumn) {
        if (parcel.getFirstLineWidth() > truckPlacement.getWidth() - truckColumn) {
            return false;
        }

        char[][] parcelChars = parcel.getAsCharArray();
        for (int parcelLine = 0; parcelLine < parcelChars.length; parcelLine++) {
            for (int parcelColumn = 0; parcelColumn < parcelChars[parcelLine].length; parcelColumn++) {
                int nextLine = truckLine + parcelLine;
                int nextColumn = truckColumn + parcelColumn;
                if (truckPlacement.isOutOfBounds(nextLine, nextColumn)
                    || truckPlacement.isFilledAtPosition(nextLine, nextColumn)
                ) {
                    return false;
                }
            }

            if (parcelLine > 0) {
                int foundationWidth = 0;
                for (int j = 0; j < parcelChars[parcelLine].length; j++) {
                    int lineAfterParcel = truckLine + parcelLine + 1;
                    int columnAfterParcel = truckColumn + j;
                    if (truckPlacement.isHeightLessThan(lineAfterParcel)
                        || (
                            !truckPlacement.isOutOfBounds(lineAfterParcel,columnAfterParcel)
                            && truckPlacement.isFilledAtPosition(lineAfterParcel,columnAfterParcel)
                            )
                        || truckLine + parcel.getHeight() == truckPlacement.getHeight()
                    ) {
                        foundationWidth++;
                    }
                }

                if (foundationWidth < Math.ceil(parcelChars[parcelLine].length / 2.0)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void placeTo(int truckLine, int truckColumn) {
        char[][] parcelChars = parcel.getAsCharArray();

        for (int i = 0; i < parcelChars.length; i++) {
            for (int j = 0; j < parcelChars[i].length; j++) {
                truckPlacement.setCharAtPosition(truckLine + i, truckColumn + j, parcelChars[i][j]);
            }
        }
    }
}
