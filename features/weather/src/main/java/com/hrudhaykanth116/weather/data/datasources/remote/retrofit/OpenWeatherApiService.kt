package com.hrudhaykanth116.weather.data.datasources.remote.retrofit

import com.hrudhaykanth116.weather.BuildConfig
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem
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

    /**
     * {
     *     "cod":400,
     *     "message":"Invalid date format",
     *     "parameters": [
     *         "date"
     *     ]
     * }
     */

    /**
     * https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
     * By default 7 days daily as per the api
     */
    @GET("data/3.0/onecall")
    suspend fun getDailyWeatherForeCast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        // @Query("units") units: String = "metric",
        // @Query("exclude") exclude: String = "hourly,minutely",
        @Query("appid") token: String = BuildConfig.OPEN_WEATHER_FORECAST_API_KEY,
    ): Response<WeatherForeCastResponse>

    @GET("geo/1.0/direct")
    suspend fun getLocationInfo(
        @Query("q") location: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") token: String = BuildConfig.OPEN_WEATHER_GEO_CODING_API_KEY,
    ): Response<List<GetLocationInfoResponseItem>>

    @GET("geo/1.0/reverse")
    suspend fun reverseGeoCoding(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") token: String = BuildConfig.OPEN_WEATHER_GEO_CODING_API_KEY,
    ): Response<List<OWMReverseGeocodingResponseItem>>

}
