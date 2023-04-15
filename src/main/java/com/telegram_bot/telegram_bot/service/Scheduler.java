package com.telegram_bot.telegram_bot.service;

import com.telegram_bot.telegram_bot.model.SubscriptionRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private BotService botService;

    @Autowired
    private SubscriptionRepositary subscriptionRepositary;

    @Autowired
    private TelegramBot telegramBot;

      // Execute every hour
    @Scheduled(cron = "0 0 * * * *")
    public void sendHourlySubscription() {
        sendSubscription("1h");
    }

    // Execute every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void sendDailySubscription() {
        sendSubscription("1d");
    }

    private void sendSubscription(String schedule) {
        subscriptionRepositary.findBySchedule(schedule).forEach(subscription -> {
            String rate = botService.getExchangeRate(subscription.getFromCurrency(), subscription.getToCurrency());
            telegramBot.SendMessage(subscription.getUser().getChatId(), rate);
        });
    }

}
