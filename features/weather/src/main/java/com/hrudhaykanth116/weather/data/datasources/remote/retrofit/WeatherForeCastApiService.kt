package com.hrudhaykanth116.weather.data.datasources.remote.retrofit

import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForeCastApiService {

    // By default 7 days daily as per the api
    @GET("data/3.0/onecall")
    suspend fun getDailyWeatherForeCast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("exclude") exclude: String = "hourly,minutely",
        @Query("appid") token: String = ApiKeys.FORECAST_API_KEY,
    ): Response<WeatherForeCastResponse>

}
