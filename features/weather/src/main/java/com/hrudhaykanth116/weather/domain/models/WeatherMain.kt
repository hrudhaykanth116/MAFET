package com.hrudhaykanth116.weather.domain.models

import androidx.annotation.DrawableRes
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

data class WeatherMain(
    val title: UIText,
    val description: UIText,
    @DrawableRes val icon: Int,
)