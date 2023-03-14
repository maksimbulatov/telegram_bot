package com.telegram_bot.telegram_bot.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "usersData")
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(unique = true)
    private Long chatId;

    private String firstName;
    private String lastName;
    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubscriptionTable> subscriptions = new HashSet<>();


}
