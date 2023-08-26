package com.hrudhaykanth116.weather.domain.models

import android.os.Parcelable
import com.hrudhaykanth116.core.data.models.UIText
import kotlinx.parcelize.Parcelize

data class WeatherHomeScreenUIState(
    val userName: String? = null,
    val userBio: String? = null,
    val userProfileImageUrl: String? = null,
    val location: String = "Kolkata",
    val locationError: UIText? = null,
    val isLoading: Boolean = true,
    val isLoggedOut: Boolean = false,
    val weatherListItemsUIState: List<WeatherListItemUIState> = listOf(),
    val errorMessage: UIText? = null,
)

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
