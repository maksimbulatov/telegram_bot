package com.telegram_bot.telegram_bot.model;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepositary extends CrudRepository<SubscriptionTable, Long> {
    //@Modifying
    //@Query("delete from SubscriptionTable s where s.user.chatId = :chatId")
    //void deleteByChatId(@Param("chatId") Long chatId);
    @Transactional
    void deleteByUser(User user);
}
