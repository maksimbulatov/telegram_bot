package com.telegram_bot.telegram_bot.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "usersData")
@Data
public class User {
    @Id
    private Long chatId;

    private String firstName;
    private String lastName;
    private String userName;

}
