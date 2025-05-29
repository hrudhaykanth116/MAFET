package com.hrudhaykanth116.weather.domain.models

import com.hrudhaykanth116.core.data.models.UIText

sealed interface WeatherHomeScreenEvent{
    data object Refresh: WeatherHomeScreenEvent
    data object Search: WeatherHomeScreenEvent
    data object OnSearchCancelled: WeatherHomeScreenEvent
    data object GpsIconClicked : WeatherHomeScreenEvent

    data class OnLocationTextChanged(val newLocationText: String): WeatherHomeScreenEvent
    data class UserMessageShown(val message: UIText): WeatherHomeScreenEvent
    class OnExpandedChange(val isExpanded: Boolean) : WeatherHomeScreenEvent
}