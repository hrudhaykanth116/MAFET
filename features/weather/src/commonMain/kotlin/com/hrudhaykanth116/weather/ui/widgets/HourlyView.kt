package com.hrudhaykanth116.weather.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.constants.Dimens
import com.hrudhaykanth116.weather.domain.models.HourlyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherMain

@Composable
fun HourlyView(
    state: List<HourlyWeatherUIState>?,
    modifier: Modifier = Modifier,
) {
    state ?: return

    HourlyViewRow(state, modifier)
}

@Composable
private fun HourlyViewRow(
    state: List<HourlyWeatherUIState>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
        contentPadding = PaddingValues(Dimens.DEFAULT_PADDING),
    ) {
        items(state) { hourlyWeatherUIState ->
            val weatherMain: WeatherMain = hourlyWeatherUIState.weatherMain

            CenteredColumn(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(25))
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(Dimens.DEFAULT_PADDING)
            ) {
                AppIcon(
                    resource = weatherMain.icon,
                    uiText = hourlyWeatherUIState.time,
                    isTextFirst = true,
                    modifier = Modifier,
                    iconModifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                VerticalSpacer(height = 4.dp)
                AppText(
                    uiText = weatherMain.title,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
