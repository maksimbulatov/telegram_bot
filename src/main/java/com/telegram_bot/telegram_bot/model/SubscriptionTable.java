package com.telegram_bot.telegram_bot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "SubscriptionTable")
@Table(name = "SubscriptionTable", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"chat_id", "from_currency", "to_currency"})
})
@Data
@NoArgsConstructor
public class SubscriptionTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private User user;

    @Column(name = "from_currency")
    private String fromCurrency;

    @Column(name = "to_currency")
    private String toCurrency;

    @Column(name = "schedule")
    private String schedule;

}
