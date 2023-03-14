package com.telegram_bot.telegram_bot.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByChatId(Long chatId);

}
