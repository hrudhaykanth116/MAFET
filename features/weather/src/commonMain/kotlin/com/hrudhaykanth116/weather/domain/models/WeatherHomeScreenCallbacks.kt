package com.hrudhaykanth116.weather.domain.models

data class WeatherHomeScreenCallbacks(
    val onLocationTextChanged: (String) -> Unit = {},
    val search: () -> Unit = {},
    val onSearchCancelled: () -> Unit = {},
    val onExpandedChange: (Boolean) -> Unit = {},
    val onGpsIconClicked: () -> Unit = {},
    val onRefreshIconClicked: () -> Unit = {},
    val onSearchIconClicked: () -> Unit = {},
)
