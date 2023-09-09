package com.hrudhaykanth116.weather.domain.models

import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.weather.domain.usecases.WeatherElement
import com.hrudhaykanth116.weather.domain.usecases.WeatherElementUIState

data class WeatherHomeScreenUIState(
    val location: String = "Hyderabad",
    val locationError: UIText? = null,
    val isLoading: Boolean = true,
    val isLoggedOut: Boolean = false,
    val weatherForeCastListItemsUIState: List<DailyWeatherUIState> = listOf(),
    val todayWeatherUIState: TodayWeatherUIState? = null,
    val errorMessage: UIText? = null,
)

data class TodayWeatherUIState(
    // val weatherTitle: UIText? = null,
    // val weatherDescription: UIText? = null,
    // val weatherIcon: ImageHolder? = null,
    val weatherMain: WeatherMain? = null,
    val weatherElementUIState: List<WeatherElementUIState>? = null,
    val time: UIText? = null,
    val weatherHourlyList: List<HourlyWeatherUIState>? = null,
) {

    companion object {

        fun default(): TodayWeatherUIState {
            return TodayWeatherUIState(
                // weatherTitle = "".toUIText(),
                // weatherDescription = "".toUIText(),
                // weatherIcon = ,
                // weatherElementUIState = listOf(),
                // time =,
                // weatherHourlyList = listOf()
            )
        }

    }

}

data class HourlyWeatherUIState(
    // val weatherElements: List<WeatherElement>,
    val weatherMain: WeatherMain,
    val time: UIText,
)

data class DailyWeatherUIState(
    val weatherElementsList: List<WeatherElement>,
    val weatherMain: WeatherMain?,
    val time: UIText,
)

// // TODO: Move the class
// data class WeatherElementUIState(
//     // Temperature in Kelvin
//     // Wind speed in meter/sec
//     // Pressure hPa
//     // Clouds %
//     // rain mm/h
//     // HUmidity %
//     val day: UIText,
//     val weather: String,
//     val humidity: String,
//     val rain: String,
//     val sunrise: UIText,
//     val sunset: UIText,
//     val maxTemp: String,
//     val minTemp: String,
//     val windSpeed: String,
//     val clouds: String,
//     val pressure: String,
// )
