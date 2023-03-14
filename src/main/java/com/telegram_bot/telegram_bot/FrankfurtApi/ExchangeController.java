package com.telegram_bot.telegram_bot.FrankfurtApi;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;

@RestController

public class ExchangeController {


    private final FrankfurterAPI frankfurterAPI;

    public ExchangeController() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        frankfurterAPI = retrofit.create(FrankfurterAPI.class);
    }

    @GetMapping("/exchange/{from}/{to}")
    public String getExchangeRate(@PathVariable("from") String from, @PathVariable("to") String to) throws IOException {
        Call<ExchangeRateResponse> call = frankfurterAPI.getExchangeRate(from,to);
        Response<ExchangeRateResponse> response = call.execute();

        if (response.isSuccessful()) {
            ExchangeRateResponse exchangeRates = response.body();
           // BigDecimal rate = exchangeRates.getRates();

            return String.format("1 %s is equal to %s", exchangeRates.base, exchangeRates.rates);
        } else {
            throw new RuntimeException("Failed to get exchange rate");
        }
    }

}
