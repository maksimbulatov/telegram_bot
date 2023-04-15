package com.telegram_bot.telegram_bot.model;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriptionRepositary extends CrudRepository<SubscriptionTable, Long> {
     @Transactional
    void deleteByUser(User user);

    List<SubscriptionTable> findByUserAndFromCurrencyAndToCurrency(User user, String fromCurrency, String toCurrency);

    List<SubscriptionTable> findBySchedule(String schedule);

}
