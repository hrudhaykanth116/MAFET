package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.constants.Dimens
import com.hrudhaykanth116.weather.domain.models.WeatherMain
import com.hrudhaykanth116.weather.domain.usecases.WeatherElementUIState

@Composable
fun TodayWeatherElements(
    state: List<WeatherElementUIState>?,
    weatherMain: WeatherMain?,
    modifier: Modifier = Modifier,
) {
    if (state == null || weatherMain == null){
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier,
        contentPadding = PaddingValues(Dimens.DEFAULT_PADDING),
        verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
        horizontalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING)
    ) {
        item(span = { GridItemSpan(this.maxLineSpan) }) {
            CurrentWeatherMain(
                weatherMain,
                modifier = Modifier.fillMaxWidth()
            )
        }

        items(state) {
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(25))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.2f),
                                Color.White.copy(alpha = 0.1f)
                            ),
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppIcon(
                    resource = it.weatherElement.displayIcon,
                    uiText = it.weatherElement.displayName,
                    isTextFirst = true,
                    modifier = Modifier,
                    iconModifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                VerticalSpacer(8.dp)
                AppText(uiText = it.value, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
