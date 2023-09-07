package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.runtime.Composable
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.weather.R
import com.hrudhaykanth116.core.R as CoreR

@Composable
fun WeatherHomeTopBar(
    location: String,
) {

    AppToolbar(
        text = location,
        navigationIcon = {},
    ) {
        AppClickableIcon(
            imageHolder = CoreR.drawable.ic_menu_vertical.toImageHolder(),
            onClick = {

            }
        )

    }

}