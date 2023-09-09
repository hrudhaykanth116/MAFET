package com.hrudhaykanth116.weather.domain.models

import com.hrudhaykanth116.core.data.models.UIText

sealed interface WeatherHomeScreenEvent{
    object Refresh: WeatherHomeScreenEvent
    object Search: WeatherHomeScreenEvent
    data class OnLocationTextChanged(val newLocationText: String): WeatherHomeScreenEvent
    data class UserMessageShown(val message: UIText): WeatherHomeScreenEvent
}