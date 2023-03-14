package com.telegram_bot.telegram_bot.FrankfurtApi;


import lombok.Data;
import retrofit2.http.Query;
import retrofit2.http.GET;
import retrofit2.Call;


public interface FrankfurterAPI {

    @GET("latest")
    public Call<ExchangeRateResponse> getExchangeRate(@Query("from") String from, @Query("to") String to);

}
