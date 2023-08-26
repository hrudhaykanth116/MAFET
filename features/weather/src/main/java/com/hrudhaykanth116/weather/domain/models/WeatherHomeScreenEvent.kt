package com.hrudhaykanth116.weather.domain.models

import com.hrudhaykanth116.core.data.models.UIText

sealed interface WeatherHomeScreenEvent{
    data class Refresh(val location: String?): WeatherHomeScreenEvent
    object LogOut: WeatherHomeScreenEvent
    data class UserMessageShown(val message: UIText): WeatherHomeScreenEvent
}