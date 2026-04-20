package com.hrudhaykanth116.composeapp.home.dashboard.models

import com.hrudhaykanth116.core.ui.models.UIText
import org.jetbrains.compose.resources.DrawableResource

data class WeatherSummary(
    val temperature: UIText,
    val condition: UIText,
    val location: String?,
    val icon: DrawableResource?,
)
