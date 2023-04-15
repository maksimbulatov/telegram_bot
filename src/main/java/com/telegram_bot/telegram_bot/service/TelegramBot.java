package com.telegram_bot.telegram_bot.service;

import com.telegram_bot.telegram_bot.Config.BotConfig;
import com.telegram_bot.telegram_bot.FrankfurtApi.ExchangeController;
import com.telegram_bot.telegram_bot.FrankfurtApi.FrankfurterAPI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.Duration;

@Component
@Slf4j
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot{

    @Autowired
    private BotService botService;
    @Autowired
    private final BotConfig botConfig;

    @Autowired
    private ExchangeController exchangeController;



    @Override
    public String getBotUsername() {
        return botConfig.getBotName();

    }
    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if(messageText.equals("/start")){
                botService.registerUser(update.getMessage());
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                //botService.testSchedule();

            }
            else if (messageText.matches("^/exchange [A-Z]{3} [A-Z]{3}$")) {
                // Extract the currency codes from the message
                String[] parts = messageText.split(" ");
                String fromCurrency = parts[1];
                String toCurrency = parts[2];

                SendMessage(chatId,botService.getExchangeRate(fromCurrency, toCurrency));

            }

            else if (messageText.matches("/subscribe [A-Z]{3} [A-Z]{3} (\\d+h|\\d+d)")){
                    String[] args = messageText.split(" ");
                    String fromCurrency = args[1];
                    String toCurrency = args[2];
                    String durationString = args[3];

                botService.subscribeUser(update.getMessage(),fromCurrency,toCurrency,durationString);


                
            } else if (messageText.matches("/unsubscribe [A-Z]{3} [A-Z]{3}")) {
                String[] args = messageText.split(" ");
                String fromCurrency = args[1];
                String toCurrency = args[2];

                botService.unsubscribeFrom(update.getMessage(),fromCurrency,toCurrency);



            } else if (messageText.equals("/unsubscribe all")){
                botService.unsubscribeAll(update.getMessage());
            }
            else {
                    SendMessage(chatId,"Sorry, command was not recognised");
                    }




            }
        }




    private void startCommandReceived(long chatId, String name) {

        String answer = "Hi, " + name + ", nice to meet you!" + "\n" +
                "Enter the currencies whose official exchange rate" + "\n" +
                "you would like to know." + "\n" +
                "For example: /exchange USD EUR" + "\n" +
                "You can also subscribe or unsubscribe for the scheduled exchange rate" + "\n" +
                " by using the following commands:" + "\n" +
                "/subscribe EUR USD 1h - for each hour" + "\n" +
                "/subscribe SGD AUD 1d - for each day" + "\n" +
                "/unsubsribe EUR USD" + "\n" +
                "/unsubsribe all";
        SendMessage(chatId, answer);


    }

    public void SendMessage(long chatId, String texToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(texToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {

        }

    }
}








