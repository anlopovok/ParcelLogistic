package ru.hofftech.parcellogistic.handler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hofftech.parcellogistic.model.OutputResult;
import ru.hofftech.parcellogistic.service.ProcessCommandService;

/**
 * Telegram bot handler that processes user commands received via Telegram.
 * This bot listens for incoming messages, processes commands using {@link ProcessCommandService},
 * and responds with appropriate messages.
 */
@Slf4j
public class TelegramBotHandler extends TelegramLongPollingBot {

    private final ProcessCommandService processCommandService;

    @Getter
    private final String botUsername;

    /**
     * Constructs a {@link TelegramBotHandler} with the given bot token, bot username, and command processing service.
     *
     * @param botToken              The bot's authentication token from Telegram.
     * @param botUsername           The bot's username on Telegram.
     * @param processCommandService The service responsible for processing user commands.
     */
    public TelegramBotHandler(String botToken, String botUsername, ProcessCommandService processCommandService) {
        super(botToken);
        this.botUsername = botUsername;
        this.processCommandService = processCommandService;
    }

    /**
     * Handles incoming updates from Telegram.
     *
     * If the update contains a text message, it processes the command and sends a response.
     *
     * @param update The update received from Telegram.
     */
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Message message = update.getMessage();
        String command = message.getText();
        log.debug("Received command: {}", command);

        try {
            OutputResult outputResult = processCommandService.processCommand(command);
            sendMessage(message.getChatId().toString(), outputResult.getMessage());
        } catch (Exception e) {
            log.error("Error processing command: {}", e.getMessage());
            sendMessage(message.getChatId().toString(), "Error: " + e.getMessage());
        }
    }

    private void sendMessage(String chatId, String messageText) {
        try {
            execute(new SendMessage(chatId, messageText));
        } catch (TelegramApiException e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }
}
