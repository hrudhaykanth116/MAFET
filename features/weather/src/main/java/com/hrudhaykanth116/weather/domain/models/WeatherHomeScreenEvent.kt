package com.hrudhaykanth116.weather.domain.models

import com.hrudhaykanth116.core.data.models.UIText

sealed interface WeatherHomeScreenEvent{
    object Refresh: WeatherHomeScreenEvent
    data class UserMessageShown(val message: UIText): WeatherHomeScreenEvent
}