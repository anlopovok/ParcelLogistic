package ru.hofftech;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.controller.ConsoleController;
import ru.hofftech.service.ParcelLogisticService;
import ru.hofftech.util.TxtParser;
import ru.hofftech.util.TxtReader;

@Slf4j
public final class Main {

    public static void main(String[] args) {
        log.info("Start application");
        ConsoleController consoleController = new ConsoleController(
                new ParcelLogisticService(
                        new TxtParser(new TxtReader())
                )
        );

        consoleController.listen();
    }
}