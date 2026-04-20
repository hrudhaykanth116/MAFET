package com.hrudhaykanth116.composeapp.home.dashboard.models

import com.hrudhaykanth116.core.ui.models.UIText
import org.jetbrains.compose.resources.DrawableResource

data class WeatherSummary(
    val title: UIText,
    val description: UIText,
    val location: String?,
    val icon: DrawableResource?,
)
