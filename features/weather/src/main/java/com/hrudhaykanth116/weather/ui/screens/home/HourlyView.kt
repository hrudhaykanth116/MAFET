package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.weather.R
import com.hrudhaykanth116.weather.domain.models.HourlyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherMain

@Composable
fun HourlyView(
    state: List<HourlyWeatherUIState>?,
    modifier: Modifier = Modifier,
) {

    state ?: return

    // TODO: Card may not be needed
    // AppCard(
    //     modifier = modifier
    //         .padding(
    //             horizontal = Dimens.DEFAULT_PADDING
    //         )
    // ) {
    HourlyViewRow(
        state
    )
    // }


}

@Composable
private fun HourlyViewRow(
    state: List<HourlyWeatherUIState>,
    modifier: Modifier = Modifier,
) {

    // .largeRadialBackground(
    //     listOf(Color(0xFF2be4dc), Color(0xFF243484))
    // )
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
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
                    // .border(border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline))
                    // .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(25))
                    .padding(Dimens.DEFAULT_PADDING)
            ) {
                AppIcon(
                    imageHolder = weatherMain.icon,
                    uiText = hourlyWeatherUIState.time,
                    isTextFirst = true,
                    modifier = Modifier,
                    iconModifier = Modifier.size(24.dp),
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

@Preview
@Composable
fun HourlyViewPreview() {
    HourlyView(
        state = listOf(
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            HourlyWeatherUIState(weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
        ),
        modifier = Modifier
    )
}