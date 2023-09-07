package com.hrudhaykanth116.weather.domain.models

import android.os.Parcelable
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class WeatherHomeScreenUIState(
    val location: String = "Kolkata",
    val locationError: UIText? = null,
    val isLoading: Boolean = true,
    val isLoggedOut: Boolean = false,
    val weatherListItemsUIState: List<WeatherListItemUIState> = listOf(),
    val currentWeatherUIState: CurrentWeatherUIState? = null,
    val errorMessage: UIText? = null,
)

data class CurrentWeatherUIState(
    val clouds: UIText,
    val dewPoint: UIText,
    val dt: UIText,
    val feelsLike: UIText,
    val humidity: UIText,
    val pressure: UIText,
    val sunrise: UIText,
    val sunset: UIText,
    val temp: UIText,
    val uvi: UIText,
    val visibility: UIText,
    val weather: Weather? = null,
    val windDeg: UIText,
    val windSpeed: UIText
){
    data class Weather(
        val description: UIText,
        val icon: ImageHolder,
        val id: UIText,
        val main: UIText
    )
}

// TODO: Move the class
@Parcelize
data class WeatherListItemUIState(
    // Temperature in Kelvin
    // Wind speed in meter/sec
    // Pressure hPa
    // Clouds %
    // rain mm/h
    // HUmidity %
    val day: String,
    val weather: String,
    val humidity: String,
    val rain: String,
    val sunrise: String,
    val sunset: String,
    val maxTemp: String,
    val minTemp: String,
    val windSpeed: String,
    val clouds: String,
    val pressure: String,
    val onClick: (() -> Unit)? = null,
): Parcelable
