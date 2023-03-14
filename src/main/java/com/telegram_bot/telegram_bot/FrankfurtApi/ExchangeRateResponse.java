package com.telegram_bot.telegram_bot.FrankfurtApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@AllArgsConstructor
public class ExchangeRateResponse {

    public Double amount;
    public String base;
    public String date;
    public Map<String, Double> rates = new HashMap<>();
}
