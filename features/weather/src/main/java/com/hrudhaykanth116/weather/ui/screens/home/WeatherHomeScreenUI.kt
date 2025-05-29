package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.theme.grey_00dp
import com.hrudhaykanth116.core.theme.grey_06dp
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.weather.R
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.TodayWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState
import com.hrudhaykanth116.weather.domain.models.WeatherMain
import com.hrudhaykanth116.weather.domain.usecases.WeatherElement
import com.hrudhaykanth116.weather.domain.usecases.WeatherElementUIState
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreenUI(
    uiState: WeatherHomeScreenUIState,
    modifier: Modifier = Modifier,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks = WeatherHomeScreenCallbacks(),
) {

    BottomSheetScaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        sheetContainerColor = Color(grey_06dp),
        topBar = {
            WeatherHomeTopBar(
                uiState.location,
                uiState.isSearchActive,
                weatherHomeScreenCallbacks,
                modifier = Modifier.fillMaxWidth()
            )
        },
        sheetContent = {
            if (!uiState.isSearchActive) {
                WeatherHomeBottomSheet(
                    uiState.weatherForeCastListItemsUIState
                )
            } else {
                // Box(modifier = Modifier.height(1.dp)) {} // Empty Box to prevent crash
            }
        },
        sheetPeekHeight = if (uiState.isSearchActive) 0.dp else 100.dp, // Control visibility
    ) {
        ContentContainer(
            uiState,
            weatherHomeScreenCallbacks = weatherHomeScreenCallbacks,
            modifier = Modifier.padding(it),
        )
    }

}

@Composable
private fun ContentContainer(
    state: WeatherHomeScreenUIState,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks,
    modifier: Modifier = Modifier,
) {

    // TODO: Handle this case Loading.
    val weather = state.todayWeatherUIState ?: return

    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacer()
        TodayWeatherElements(
            state.todayWeatherUIState.weatherElementUIState,
            weather.weatherMain,
            modifier = Modifier.fillMaxWidth()
        )
        VerticalSpacer()
        HourlyView(
            state.todayWeatherUIState.weatherHourlyList,
            modifier = Modifier.fillMaxWidth()
        )

    }


}

@MyPreview
@Composable
fun WeatherHomeScreenUIPreview(

) {

    WeatherHomeScreenUI(
        uiState = WeatherHomeScreenUIState(
            location = "Bangalore",
            isSearchActive = true,
            todayWeatherUIState = TodayWeatherUIState(
                weatherElementUIState = persistentListOf(
                    WeatherElementUIState(
                        weatherElement = WeatherElement.TEMP,
                        value = "Atmosphere".toUIText()
                    )
                ),

                ),
            isLoading = false,
            weatherForeCastListItemsUIState = listOf(
                DailyWeatherUIState(
                    weatherElementsList = listOf(
                        WeatherElement.TEMP
                    ),
                    weatherMain = WeatherMain(
                        title = "Atmosphere".toUIText(),
                        description = "sdflksf".toUIText(),
                        icon = ImageHolder.LocalDrawableResource(R.drawable.ic_atmosphere)
                    ),
                    time = "Today".toUIText()
                )
            )
        )
    )

}