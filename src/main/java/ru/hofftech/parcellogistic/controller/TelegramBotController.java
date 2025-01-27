package ru.hofftech.parcellogistic.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hofftech.parcellogistic.handler.TelegramBotHandler;

/**
 * Controller for managing a Telegram bot.
 * This class initializes and registers a Telegram bot, allowing it to listen for commands and messages.
 */
@Slf4j
@AllArgsConstructor
public class TelegramBotController {

    private final TelegramBotHandler telegramBotHandler;

    /**
     * Starts listening for incoming Telegram bot commands.
     * Registers the bot with the Telegram API and logs any errors encountered during registration.
     */
    public void listen() {
        log.info("Start listening telegram bot commands...");

        try {
            log.debug("Регистрация телеграм бота");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBotHandler);
        } catch (TelegramApiException e) {
            log.error("Error registering the bot: {}", e.getMessage());
        }
    }
}
