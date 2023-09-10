package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.weather.R
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherMain

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

        // TODO: Add something like this
        // item {
        //     AppText(uiText = "Next {7} days".toUIText())
        // }

        items(dailyWeatherUIStateList) { item ->

            item.weatherMain?.let { weatherMain ->
                BottomSheetRow(item.time, weatherMain)
            }

        }
    }
}

@Composable
private fun BottomSheetRow(
    time: UIText,
    weatherMain: WeatherMain,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            // .clip(RoundedCornerShape(50))
            // .border(
            //     width = 20.dp, color = Color.LightGray
            // )
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(50))
            .padding(
                vertical = Dimens.DEFAULT_PADDING,
                horizontal = Dimens.DEFAULT_PADDING * 2,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppText(
            uiText = time,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalSpacer()
        AppIcon(imageHolder = weatherMain.icon, iconModifier = Modifier.size(30.dp))
        HorizontalSpacer()
        AppText(uiText = weatherMain.title, style = MaterialTheme.typography.titleLarge)
    }
}

@MyPreview
@Composable
fun WeatherHomeBottomSheetPreview() {
    WeatherHomeBottomSheet(
        dailyWeatherUIStateList = listOf(
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
            DailyWeatherUIState(listOf(), weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()), time = "22 09".toUIText()),
        )
    )
}

@MyPreview
@Composable
private fun RowPreview() {
    BottomSheetRow(
        time = "df".toUIText(),
        weatherMain = WeatherMain("Cloudy".toUIText(), "Rainy".toUIText(), R.drawable.ic_clouds.toImageHolder()),
        modifier = Modifier.height(100.dp).background(color = Color.Red).padding(20.dp)
        )
}