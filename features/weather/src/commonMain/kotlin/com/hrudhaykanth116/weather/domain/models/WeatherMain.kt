package com.hrudhaykanth116.weather.domain.models

import com.hrudhaykanth116.core.ui.models.UIText
import org.jetbrains.compose.resources.DrawableResource

data class WeatherMain(
    val title: UIText,
    val description: UIText,
    val icon: DrawableResource,
)
