package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.utils.compose.modifier.largeRadialBackground
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.weather.domain.models.WeatherMain
import com.hrudhaykanth116.weather.domain.usecases.WeatherElement
import com.hrudhaykanth116.weather.domain.usecases.WeatherElementUIState

@Composable
fun TodayWeatherElements(
    state: List<WeatherElementUIState>?,
    weatherMain: WeatherMain?,
    modifier: Modifier = Modifier,
) {

    if (state == null || weatherMain == null) return

    // TODO: Card may not be needed
    val appCardModifier = modifier
        // .padding(
        //     Dimens.DEFAULT_PADDING * 4
        // )
    AppCard(
        modifier = appCardModifier,
        cornerPercent = 5,
        borderStroke = null,
    ) {

        // TODO: Static Grid may be enough
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .largeRadialBackground(
                    listOf(
                        Color(0xFF04253A),
                        Color(0xFF021D2C)
                    )
                )
                .heightIn(max = 360.dp)
                // .padding(Dimens.DEFAULT_PADDING)
            ,
            contentPadding = PaddingValues(Dimens.DEFAULT_PADDING),
            verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
            horizontalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING)
        ) {

            item(span = { GridItemSpan(this.maxLineSpan) }) {
                CurrentWeatherMain(
                    weatherMain,
                    modifier = Modifier
                        .fillMaxWidth()
                        // .padding(bottom = Dimens.DEFAULT_PADDING),
                )
            }

            items(state) {
                Column(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(25))
                        .background(
                            // color = MaterialTheme.colorScheme.surface
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.2f),
                                    Color.White.copy(alpha = 0.1f)
                                ),
                                // startY = 0.0f,
                                // endY = 400.0f
                            )
                        )
                        // .border(border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline))
                        // .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(25))
                        .padding(Dimens.DEFAULT_PADDING),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppIcon(
                        imageHolder = it.weatherElement.displayIcon,
                        uiText = it.weatherElement.displayName,
                        isTextFirst = true,
                        modifier = Modifier,
                        iconModifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                    AppText(uiText = it.value, style = MaterialTheme.typography.bodyMedium)
                }
            }

        }

    }


}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun TodayWeatherElementsPreview() {
    val sampleWeatherElements = listOf(
        WeatherElementUIState(
            weatherElement = WeatherElement.WIND_SPEED,
            value = "15 km/h".toUIText()
        ),
        WeatherElementUIState(
            weatherElement = WeatherElement.HUMIDITY,
            value = "65%".toUIText()
        ),
        WeatherElementUIState(
            weatherElement = WeatherElement.PRESSURE,
            value = "1012 hPa".toUIText()
        ),
        WeatherElementUIState(
            weatherElement = WeatherElement.VISIBILITY,
            value = "10 km".toUIText()
        ),
        WeatherElementUIState(
            weatherElement = WeatherElement.UVI,
            value = "High".toUIText()
        ),
        WeatherElementUIState(
            weatherElement = WeatherElement.SUNRISE,
            value = "6:00 AM".toUIText()
        )
    )

    val sampleWeatherMain = WeatherMain(
        title = "Temperature".toUIText(),
        description = "25°C".toUIText(),
        icon = WeatherElement.TEMP.displayIcon
    )

    TodayWeatherElements(
        state = sampleWeatherElements,
        weatherMain = sampleWeatherMain
    )
}