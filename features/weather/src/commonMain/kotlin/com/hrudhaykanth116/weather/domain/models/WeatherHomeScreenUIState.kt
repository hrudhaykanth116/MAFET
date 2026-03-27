package com.hrudhaykanth116.weather.domain.models

import com.hrudhaykanth116.core.data.ErrorState
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.weather.domain.usecases.WeatherElement
import com.hrudhaykanth116.weather.domain.usecases.WeatherElementUIState
import kotlinx.collections.immutable.ImmutableList

data class WeatherHomeScreenUIState(
    val location: String? = null,
    val searchText: String? = null,
    val locationError: UIText? = null,
    val isSearchActive: Boolean = false,
    val weatherForeCastListItemsUIState: List<DailyWeatherUIState> = listOf(),
    val todayWeatherUIState: TodayWeatherUIState? = null,
    val errorState: ErrorState? = null
)

data class TodayWeatherUIState(
    val weatherMain: WeatherMain? = null,
    val weatherElementUIState: ImmutableList<WeatherElementUIState>? = null,
    val time: UIText? = null,
    val weatherHourlyList: ImmutableList<HourlyWeatherUIState>? = null,
) {
    companion object {
        fun default(): TodayWeatherUIState {
            return TodayWeatherUIState()
        }
    }
}

data class HourlyWeatherUIState(
    val weatherMain: WeatherMain,
    val time: UIText,
)

data class DailyWeatherUIState(
    val weatherElementsList: List<WeatherElement>,
    val weatherMain: WeatherMain?,
    val time: UIText,
)
