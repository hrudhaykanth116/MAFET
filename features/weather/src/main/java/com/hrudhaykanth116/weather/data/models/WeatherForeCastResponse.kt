package com.hrudhaykanth116.weather.data.models


import com.squareup.moshi.Json

// @JsonClass(generateAdapter = true)
data class WeatherForeCastResponse(
    @field:Json(name = "current")
    val current: Current? = null,
    @field:Json(name = "daily")
    val daily: List<Daily?>? = null,
    @field:Json(name = "lat")
    val lat: Double? = null,
    @field:Json(name = "lon")
    val lon: Double? = null,
    @field:Json(name = "timezone")
    val timezone: String? = null,
    @field:Json(name = "timezone_offset")
    val timezoneOffset: Int? = null
) {
    // @field:JsonClass(generateAdapter = true)
    data class Current(
        @field:Json(name = "clouds")
        val clouds: Float? = null,
        @field:Json(name = "dew_point")
        val dewPoint: Double? = null,
        @field:Json(name = "dt")
        val dt: Float? = null,
        @field:Json(name = "feels_like")
        val feelsLike: Double? = null,
        @field:Json(name = "humidity")
        val humidity: Float? = null,
        @field:Json(name = "pressure")
        val pressure: Float? = null,
        @field:Json(name = "sunrise")
        val sunrise: Float? = null,
        @field:Json(name = "sunset")
        val sunset: Float? = null,
        @field:Json(name = "temp")
        val temp: Double? = null,
        @field:Json(name = "uvi")
        val uvi: Double? = null,
        @field:Json(name = "visibility")
        val visibility: Float? = null,
        @field:Json(name = "weather")
        val weather: List<Weather?>? = null,
        @field:Json(name = "wind_deg")
        val windDeg: Float? = null,
        @field:Json(name = "wind_gust")
        val windGust: Double? = null,
        @field:Json(name = "wind_speed")
        val windSpeed: Double? = null
    ) {
        // @field:JsonClass(generateAdapter = true)
        data class Weather(
            @field:Json(name = "description")
            val description: String? = null,
            @field:Json(name = "icon")
            val icon: String? = null,
            @field:Json(name = "id")
            val id: Float? = null,
            @field:Json(name = "main")
            val main: String? = null
        )
    }

    // @field:JsonClass(generateAdapter = true)
    data class Daily(
        @field:Json(name = "clouds")
        val clouds: Float? = null,
        @field:Json(name = "dew_point")
        val dewPoint: Double? = null,
        @field:Json(name = "dt")
        val dt: Int? = null,
        @field:Json(name = "feels_like")
        val feelsLike: FeelsLike? = null,
        @field:Json(name = "humidity")
        val humidity: Float? = null,
        @field:Json(name = "moon_phase")
        val moonPhase: Double? = null,
        @field:Json(name = "moonrise")
        val moonrise: Float? = null,
        @field:Json(name = "moonset")
        val moonset: Float? = null,
        @field:Json(name = "pop")
        val pop: Double? = null,
        @field:Json(name = "pressure")
        val pressure: Float? = null,
        @field:Json(name = "rain")
        val rain: Double? = null,
        @field:Json(name = "sunrise")
        val sunrise: Int? = null,
        @field:Json(name = "sunset")
        val sunset: Int? = null,
        @field:Json(name = "temp")
        val temp: Temp? = null,
        @field:Json(name = "uvi")
        val uvi: Double? = null,
        @field:Json(name = "weather")
        val weather: List<Weather?>? = null,
        @field:Json(name = "wind_deg")
        val windDeg: Float? = null,
        @field:Json(name = "wind_gust")
        val windGust: Double? = null,
        @field:Json(name = "wind_speed")
        val windSpeed: Double? = null
    ) {
        // @field:JsonClass(generateAdapter = true)
        data class FeelsLike(
            @field:Json(name = "day")
            val day: Double? = null,
            @field:Json(name = "eve")
            val eve: Double? = null,
            @field:Json(name = "morn")
            val morn: Double? = null,
            @field:Json(name = "night")
            val night: Double? = null
        )

        // @field:JsonClass(generateAdapter = true)
        data class Temp(
            @field:Json(name = "day")
            val day: Float? = null,
            @field:Json(name = "eve")
            val eve: Float? = null,
            @field:Json(name = "max")
            val max: Float? = null,
            @field:Json(name = "min")
            val min: Float? = null,
            @field:Json(name = "morn")
            val morn: Float? = null,
            @field:Json(name = "night")
            val night: Float? = null
        )

        // @field:JsonClass(generateAdapter = true)
        data class Weather(
            @field:Json(name = "description")
            val description: String? = null,
            @field:Json(name = "icon")
            val icon: String? = null,
            @field:Json(name = "id")
            val id: Float? = null,
            @field:Json(name = "main")
            val main: String? = null
        )
    }
}