package com.hrudhaykanth116.weather.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForeCastResponse(
    val current: Current? = null,
    val daily: List<Daily?>? = null,
    val hourly: List<Hourly?>? = null,
    val lat: Float? = null,
    val lon: Float? = null,
    val minutely: List<Minutely?>? = null,
    val timezone: String? = null,
    @SerialName("timezone_offset")
    val timezoneOffset: Int? = null
) {
    @Serializable
    data class Current(
        val clouds: Float? = null,
        @SerialName("dew_point")
        val dewPoint: Float? = null,
        val dt: Int? = null,
        @SerialName("feels_like")
        val feelsLike: Float? = null,
        val humidity: Float? = null,
        val pressure: Float? = null,
        val sunrise: Int? = null,
        val sunset: Int? = null,
        val temp: Float? = null,
        val uvi: Float? = null,
        val visibility: Float? = null,
        val weather: List<Weather?>? = null,
        @SerialName("wind_deg")
        val windDeg: Float? = null,
        @SerialName("wind_speed")
        val windSpeed: Float? = null
    ) {
        @Serializable
        data class Weather(
            val description: String? = null,
            val icon: String? = null,
            val id: Int? = null,
            val main: String? = null
        )
    }

    @Serializable
    data class Daily(
        val clouds: Float? = null,
        @SerialName("dew_point")
        val dewPoint: Float? = null,
        val dt: Int? = null,
        @SerialName("feels_like")
        val feelsLike: FeelsLike? = null,
        val humidity: Float? = null,
        @SerialName("moon_phase")
        val moonPhase: Float? = null,
        val moonrise: Int? = null,
        val moonset: Int? = null,
        val pop: Float? = null,
        val pressure: Float? = null,
        val rain: Float? = null,
        val summary: String? = null,
        val sunrise: Int? = null,
        val sunset: Int? = null,
        val temp: Temp? = null,
        val uvi: Float? = null,
        val weather: List<Weather?>? = null,
        @SerialName("wind_deg")
        val windDeg: Float? = null,
        @SerialName("wind_gust")
        val windGust: Float? = null,
        @SerialName("wind_speed")
        val windSpeed: Float? = null
    ) {
        @Serializable
        data class FeelsLike(
            val day: Float? = null,
            val eve: Float? = null,
            val morn: Float? = null,
            val night: Float? = null
        )

        @Serializable
        data class Temp(
            val day: Float? = null,
            val eve: Float? = null,
            val max: Float? = null,
            val min: Float? = null,
            val morn: Float? = null,
            val night: Float? = null
        )

        @Serializable
        data class Weather(
            val description: String? = null,
            val icon: String? = null,
            val id: Int? = null,
            val main: String? = null
        )
    }

    @Serializable
    data class Hourly(
        val clouds: Float? = null,
        @SerialName("dew_point")
        val dewPoint: Float? = null,
        val dt: Int? = null,
        @SerialName("feels_like")
        val feelsLike: Float? = null,
        val humidity: Float? = null,
        val pop: Float? = null,
        val pressure: Float? = null,
        val rain: Rain? = null,
        val temp: Float? = null,
        val uvi: Float? = null,
        val visibility: Float? = null,
        val weather: List<Weather?>? = null,
        @SerialName("wind_deg")
        val windDeg: Float? = null,
        @SerialName("wind_gust")
        val windGust: Float? = null,
        @SerialName("wind_speed")
        val windSpeed: Float? = null
    ) {
        @Serializable
        data class Rain(
            @SerialName("1h")
            val h: Float? = null
        )

        @Serializable
        data class Weather(
            val description: String? = null,
            val icon: String? = null,
            val id: Int? = null,
            val main: String? = null
        )
    }

    @Serializable
    data class Minutely(
        val dt: Int? = null,
        val precipitation: Float? = null
    )
}
