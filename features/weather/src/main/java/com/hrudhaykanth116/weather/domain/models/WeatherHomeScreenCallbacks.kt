package com.hrudhaykanth116.weather.domain.models

import androidx.compose.ui.text.input.TextFieldValue

data class WeatherHomeScreenCallbacks(
    val onLocationTextChanged: (String) -> Unit = {},
    val search: () -> Unit = {},
    val onSearchCancelled: () -> Unit = {},
    val onExpandedChange: (Boolean) -> Unit = {},
    val onGpsIconClicked: () -> Unit = {},
)
