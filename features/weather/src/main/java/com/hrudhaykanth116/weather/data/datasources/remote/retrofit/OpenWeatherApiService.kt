package com.hrudhaykanth116.weather.data.datasources.remote.retrofit

import com.hrudhaykanth116.weather.confidential.ApiKeys
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {

    /**
     * Api documentation: https://openweathermap.org/api/one-call-3
     * https://home.openweathermap.org/api_keys First key.
     * Billing: https://home.openweathermap.org/subscriptions. Free limit per day: 1,000 calls. Current set as 500.
     *
     */

    // By default 7 days daily as per the api
    @GET("data/3.0/onecall")
    suspend fun getDailyWeatherForeCast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("exclude") exclude: String = "hourly,minutely",
        @Query("appid") token: String = ApiKeys.FORECAST_API_KEY,
    ): Response<WeatherForeCastResponse>

    @GET("geo/1.0/direct")
    suspend fun getLocationInfo(
        @Query("q") location: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") token: String = ApiKeys.GEO_CODING_API_KEY,
    ): Response<List<GetLocationInfoResponseItem>>

}
