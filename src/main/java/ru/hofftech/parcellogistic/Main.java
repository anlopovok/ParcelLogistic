package ru.hofftech.parcellogistic;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcellogistic.controller.ConsoleController;
import ru.hofftech.parcellogistic.service.ParcelLogisticService;
import ru.hofftech.parcellogistic.service.TruckPlacementServiceFactory;
import ru.hofftech.parcellogistic.util.TxtParser;
import ru.hofftech.parcellogistic.util.TxtReader;

@Slf4j
public final class Main {

    public static void main(String[] args) {
        log.info("Start application");
        ConsoleController consoleController = new ConsoleController(
                new ParcelLogisticService(
                        new TruckPlacementServiceFactory(),
                        new TxtParser(new TxtReader())
                )
        );

        consoleController.listen();
    }
}