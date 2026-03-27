package com.hrudhaykanth116.weather.data.datasources.remote.ktor

import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class OpenWeatherApiServiceKtor(
    private val httpClient: HttpClient
) {

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
    }

    suspend fun getDailyWeatherForeCast(
        latitude: String,
        longitude: String,
        token: String = WeatherApiConfig.forecastApiKey,
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

    suspend fun getLocationInfo(
        location: String,
        limit: Int = 1,
        token: String = WeatherApiConfig.geoCodingApiKey,
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

    suspend fun reverseGeoCoding(
        latitude: String,
        longitude: String,
        limit: Int = 1,
        token: String = WeatherApiConfig.geoCodingApiKey,
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
