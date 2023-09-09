package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.utils.compose.modifier.largeRadialBackground
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.weather.domain.models.HourlyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherMain

@Composable
fun HourlyView(
    state: List<HourlyWeatherUIState>?,
    modifier: Modifier = Modifier,
) {

    state ?: return

    // TODO: Card may not be needed
    AppCard(
        modifier = modifier
            .padding(
                horizontal = Dimens.DEFAULT_PADDING
            )
    ) {

        LazyRow(
            modifier = Modifier
                .largeRadialBackground(
                    listOf(Color(0xFF2be4dc), Color(0xFF243484))
                ),
            horizontalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
            contentPadding = PaddingValues(Dimens.DEFAULT_PADDING),
        ) {
            items(state) { hourlyWeatherUIState ->

                val weatherMain: WeatherMain = hourlyWeatherUIState.weatherMain

                CenteredColumn(
                    modifier = Modifier
                    // .background(
                    //     color = Color.Red
                    // )
                ) {
                    AppIcon(
                        imageHolder = weatherMain.icon,
                        uiText = hourlyWeatherUIState.time,
                        isTextFirst = true,
                        modifier = Modifier,
                        iconModifier = Modifier.size(50.dp),
                        tint = Color.Unspecified
                    )
                    AppText(
                        uiText = weatherMain.title,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        }
    }


}