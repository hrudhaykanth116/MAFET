package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.weather.R
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.HourlyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.TodayWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState
import com.hrudhaykanth116.weather.domain.models.WeatherMain
import com.hrudhaykanth116.weather.domain.usecases.WeatherElement
import com.hrudhaykanth116.weather.domain.usecases.WeatherElementUIState
import kotlinx.collections.immutable.persistentListOf

/**
 * Provides sample data for WeatherHomeScreenUI previews.
 * Generates different UI states to preview various scenarios.
 */
class WeatherHomeScreenUIStateProvider :
    PreviewParameterProvider<UIState<WeatherHomeScreenUIState>> {

    override val values: Sequence<UIState<WeatherHomeScreenUIState>>
        get() = sequenceOf(
            createIdleState(),
            createLoadingState(),
            createLoadingStateWithContent()
        )

    private fun createIdleState(): UIState.Idle<WeatherHomeScreenUIState> {
        return UIState.Idle(
            contentState = WeatherHomeScreenUIState(
                location = "Bangalore",
                isSearchActive = false,
                todayWeatherUIState = TodayWeatherUIState(
                    weatherElementUIState = createSampleWeatherElements(),
                    weatherMain = createSampleWeatherMain(),
                    weatherHourlyList = createHourlyWeather()
                ),
                weatherForeCastListItemsUIState = createDailyForecast()
            )
        )
    }

    private fun createLoadingState(): UIState.Loading<WeatherHomeScreenUIState> {
        return UIState.Loading(
            contentState = null,
            message = "Loading weather data...".toUIText()
        )
    }

    private fun createLoadingStateWithContent(): UIState.Loading<WeatherHomeScreenUIState> {
        return UIState.Loading(
            contentState = WeatherHomeScreenUIState(
                location = "Bangalore",
                isSearchActive = false,
                todayWeatherUIState = TodayWeatherUIState(
                    weatherElementUIState = createSampleWeatherElements(),
                    weatherMain = createSampleWeatherMain(),
                    weatherHourlyList = createHourlyWeather()
                ),
                weatherForeCastListItemsUIState = createDailyForecast()
            ),
            message = "Refreshing...".toUIText()
        )
    }

    companion object {
        fun createSampleWeatherElements() = persistentListOf(
            WeatherElementUIState(
                weatherElement = WeatherElement.WIND_SPEED,
                value = "15 km/h".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.HUMIDITY,
                value = "65%".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.PRESSURE,
                value = "1012 hPa".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.VISIBILITY,
                value = "10 km".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.UVI,
                value = "High".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.SUNRISE,
                value = "6:00 AM".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.PRESSURE,
                value = "1012 hPa".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.VISIBILITY,
                value = "10 km".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.UVI,
                value = "High".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.SUNRISE,
                value = "6:00 AM".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.UVI,
                value = "High".toUIText()
            ),
            WeatherElementUIState(
                weatherElement = WeatherElement.SUNRISE,
                value = "6:00 AM".toUIText()
            )
        )

        fun createSampleWeatherMain() = WeatherMain(
            title = "Temperature".toUIText(),
            description = "25°C".toUIText(),
            icon = WeatherElement.TEMP.displayIcon
        )

        fun createHourlyWeather() = persistentListOf(
            HourlyWeatherUIState(
                weatherMain = WeatherMain(
                    "Cloudy".toUIText(),
                    "Rainy".toUIText(),
                    R.drawable.ic_clouds
                ), time = "22 09".toUIText()
            ),
            HourlyWeatherUIState(
                weatherMain = WeatherMain(
                    "Cloudy".toUIText(),
                    "Rainy".toUIText(),
                    R.drawable.ic_clouds
                ), time = "23 09".toUIText()
            ),
            HourlyWeatherUIState(
                weatherMain = WeatherMain(
                    "Partly Cloudy".toUIText(),
                    "Partly Cloudy".toUIText(),
                    R.drawable.ic_clouds
                ), time = "00 10".toUIText()
            ),
            HourlyWeatherUIState(
                weatherMain = WeatherMain(
                    "Sunny".toUIText(),
                    "Clear Sky".toUIText(),
                    R.drawable.ic_clear
                ), time = "01 10".toUIText()
            ),
            HourlyWeatherUIState(
                weatherMain = WeatherMain(
                    "Cloudy".toUIText(),
                    "Rainy".toUIText(),
                    R.drawable.ic_clouds
                ), time = "03 10".toUIText()
            ),
            HourlyWeatherUIState(
                weatherMain = WeatherMain(
                    "Rainy".toUIText(),
                    "Heavy Rain".toUIText(),
                    R.drawable.ic_rain
                ), time = "04 10".toUIText()
            ),
            HourlyWeatherUIState(
                weatherMain = WeatherMain(
                    "Rainy".toUIText(),
                    "Light Rain".toUIText(),
                    R.drawable.ic_rain
                ), time = "05 10".toUIText()
            ),
            HourlyWeatherUIState(
                weatherMain = WeatherMain(
                    "Cloudy".toUIText(),
                    "Overcast".toUIText(),
                    R.drawable.ic_clouds
                ), time = "06 10".toUIText()
            ),
            HourlyWeatherUIState(
                weatherMain = WeatherMain(
                    "Partly Cloudy".toUIText(),
                    "Partly Cloudy".toUIText(),
                    R.drawable.ic_clouds
                ), time = "07 10".toUIText()
            ),
        )

        fun createDailyForecast() = listOf(
            DailyWeatherUIState(
                weatherElementsList = listOf(WeatherElement.TEMP),
                weatherMain = WeatherMain(
                    title = "Atmosphere".toUIText(),
                    description = "Partly cloudy".toUIText(),
                    icon = R.drawable.ic_atmosphere
                ),
                time = "Today".toUIText()
            ),
            DailyWeatherUIState(
                weatherElementsList = listOf(WeatherElement.TEMP),
                weatherMain = WeatherMain(
                    title = "Rainy".toUIText(),
                    description = "Heavy rain".toUIText(),
                    icon = R.drawable.ic_rain
                ),
                time = "Wed".toUIText()
            )
        )
    }
}