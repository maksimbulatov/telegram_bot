package com.telegram_bot.telegram_bot.service;

import com.telegram_bot.telegram_bot.FrankfurtApi.ExchangeController;
import com.telegram_bot.telegram_bot.model.SubscriptionRepositary;
import com.telegram_bot.telegram_bot.model.SubscriptionTable;
import com.telegram_bot.telegram_bot.model.User;
import com.telegram_bot.telegram_bot.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;


@Service
public class BotService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepositary subscriptionRepositary;

    @Autowired
    private ExchangeController exchangeController;

    public void registerUser(Message msg){

        if (userRepository.findById(msg.getChatId()).isEmpty()){

            Long chatId = msg.getChatId();

            Chat chat = msg.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());

            userRepository.save(user);

        }



    }

    public void subscribeUser(Message msg, String fromCurrency, String toCurrency, String duration){
        if (userRepository.findById(msg.getChatId()).isPresent())
        {
            User user = userRepository.findByChatId(msg.getChatId());
            SubscriptionTable subscriptionTable = new SubscriptionTable();
            subscriptionTable.setUser(user);
            subscriptionTable.setFromCurrency(fromCurrency);
            subscriptionTable.setToCurrency(toCurrency);
            subscriptionTable.setSchedule(duration);

            subscriptionRepositary.save(subscriptionTable);
        }


    }

    public void unsubscribeAll(Message msg) {
        if (subscriptionRepositary.findById(msg.getChatId()).isPresent()) {
            Long chatId = msg.getChatId();
            User user = userRepository.findByChatId(chatId);
            subscriptionRepositary.deleteByUser(user);
        }
    }

    public String getExchangeRate(String fromCurrency, String toCurrency) {

        String rate;
        try {
            rate = exchangeController.getExchangeRate(fromCurrency, toCurrency);
            //SendMessage(chatId,rate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rate.replaceAll("[{}\\[\\]]", "");
    }

//    @Scheduled(cron = "* * * * * *")
//    public void testSchedule(){
//        System.out.println("test works!");
//    }






}
