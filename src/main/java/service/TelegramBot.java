package service;

import Config.BotConfig;
import lombok.AllArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

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

            switch (messageText){
                case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;

                default:
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
    private void SendMessage(long chatId, String texToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(texToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);

        }

    }
}








