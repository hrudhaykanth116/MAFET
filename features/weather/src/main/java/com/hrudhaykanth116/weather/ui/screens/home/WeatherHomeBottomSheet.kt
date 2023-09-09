package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState

@Composable
fun WeatherHomeBottomSheet(
    dailyWeatherUIStateList: List<DailyWeatherUIState>,
    modifier: Modifier = Modifier,
) {


    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = Dimens.DEFAULT_PADDING),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(dailyWeatherUIStateList) { item ->

            item.weatherMain?.let { weatherMain ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    // modifier = Modifier.background(color = Color.Yellow )
                ) {
                    AppText(uiText = item.time, modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleLarge)
                    HorizontalSpacer()
                    AppIcon(imageHolder = weatherMain.icon, iconModifier = Modifier.size(30.dp))
                    HorizontalSpacer()
                    AppText(uiText = weatherMain.title, style = MaterialTheme.typography.titleLarge)
                }
            }

        }
    }
}