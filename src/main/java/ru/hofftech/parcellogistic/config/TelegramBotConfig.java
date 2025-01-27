package ru.hofftech.parcellogistic.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TelegramBotConfig {

    private final Dotenv dotenv;

    public String getToken() {
        return dotenv.get("TELEGRAM_BOT_TOKEN");
    }

    public String getUsername() {
        return dotenv.get("TELEGRAM_BOT_USERNAME");
    }
}
