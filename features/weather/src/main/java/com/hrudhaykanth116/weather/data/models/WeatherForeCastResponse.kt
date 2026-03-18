package com.hrudhaykanth116.weather.data.models


import com.squareup.moshi.Json
import kotlinx.collections.immutable.ImmutableList
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
    @Json(name = "timezone_offset")
    @SerialName("timezone_offset")
    val timezoneOffset: Int? = null
) {
    @Serializable
    data class Current(
        val clouds: Float? = null,
        @Json(name = "dew_point")
        @SerialName("dew_point")
        val dewPoint: Float? = null,
        val dt: Int? = null,
        @Json(name = "feels_like")
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
        @Json(name = "wind_deg")
        @SerialName("wind_deg")
        val windDeg: Float? = null,
        @Json(name = "wind_speed")
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
        @Json(name = "dew_point")
        @SerialName("dew_point")
        val dewPoint: Float? = null,
        val dt: Int? = null,
        @Json(name = "feels_like")
        @SerialName("feels_like")
        val feelsLike: FeelsLike? = null,
        val humidity: Float? = null,
        @Json(name = "moon_phase")
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
        @Json(name = "wind_deg")
        @SerialName("wind_deg")
        val windDeg: Float? = null,
        @Json(name = "wind_gust")
        @SerialName("wind_gust")
        val windGust: Float? = null,
        @Json(name = "wind_speed")
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
        @Json(name = "dew_point")
        @SerialName("dew_point")
        val dewPoint: Float? = null,
        val dt: Int? = null,
        @Json(name = "feels_like")
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
        @Json(name = "wind_deg")
        @SerialName("wind_deg")
        val windDeg: Float? = null,
        @Json(name = "wind_gust")
        @SerialName("wind_gust")
        val windGust: Float? = null,
        @Json(name = "wind_speed")
        @SerialName("wind_speed")
        val windSpeed: Float? = null
    ) {
        @Serializable
        data class Rain(
            @Json(name = "1h")
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