package com.hrudhaykanth116.weather.data.datasources.remote.ktor

import com.hrudhaykanth116.weather.BuildConfig
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Ktor-based OpenWeather API service using HttpClient from core-network module
 *
 * Api documentation: https://openweathermap.org/api/one-call-3
 * https://home.openweathermap.org/api_keys First key.
 * Billing: https://home.openweathermap.org/subscriptions. Free limit per day: 1,000 calls. Current set as 500.
 */
class OpenWeatherApiServiceKtor(
    private val httpClient: HttpClient
) {

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
    }

    /**
     * Get daily weather forecast
     * https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
     * By default returns 7 days daily forecast as per the API
     */
    suspend fun getDailyWeatherForeCast(
        latitude: String,
        longitude: String,
        token: String = BuildConfig.OPEN_WEATHER_FORECAST_API_KEY,
    ): Result<WeatherForeCastResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}data/3.0/onecall") {
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("appid", token)
            }.body<WeatherForeCastResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get location information by location name
     * https://api.openweathermap.org/geo/1.0/direct?q={location}&limit={limit}&appid={API key}
     */
    suspend fun getLocationInfo(
        location: String,
        limit: Int = 1,
        token: String = BuildConfig.OPEN_WEATHER_GEO_CODING_API_KEY,
    ): Result<List<GetLocationInfoResponseItem>> {
        return try {
            val response = httpClient.get("${BASE_URL}geo/1.0/direct") {
                parameter("q", location)
                parameter("limit", limit)
                parameter("appid", token)
            }.body<List<GetLocationInfoResponseItem>>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Reverse geocoding - get location name from coordinates
     * https://api.openweathermap.org/geo/1.0/reverse?lat={lat}&lon={lon}&limit={limit}&appid={API key}
     */
    suspend fun reverseGeoCoding(
        latitude: String,
        longitude: String,
        limit: Int = 1,
        token: String = BuildConfig.OPEN_WEATHER_GEO_CODING_API_KEY,
    ): Result<List<OWMReverseGeocodingResponseItem>> {
        return try {
            val response = httpClient.get("${BASE_URL}geo/1.0/reverse") {
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("limit", limit)
                parameter("appid", token)
            }.body<List<OWMReverseGeocodingResponseItem>>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
