package com.telegram_bot.telegram_bot;

import com.telegram_bot.telegram_bot.Config.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.telegram_bot.telegram_bot.service.TelegramBot;

@SpringBootApplication
public class TelegramBotApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(TelegramBotApplication.class, args);

	}

}
