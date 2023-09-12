package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.hrudhaykanth116.core.theme.grey_00dp
import com.hrudhaykanth116.core.theme.grey_06dp
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState

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
                weatherHomeScreenCallbacks,
            )
        },
        sheetContent = {
            WeatherHomeBottomSheet(
                uiState.weatherForeCastListItemsUIState
            )
        },
        sheetPeekHeight = 100.dp,
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
    // WeatherHomeScreenUI(
    //     uiState = WeatherHomeScreenUIState(
    //         currentWeatherUIState = CurrentWeatherUIState(
    //             clouds = "51".toUIText(),
    //             dewPoint = "null".toUIText(),
    //             dt = "null".toUIText(),
    //             feelsLike = "null".toUIText(),
    //             humidity = "null".toUIText(),
    //             pressure = "null".toUIText(),
    //             sunrise = "null".toUIText(),
    //             sunset = "null".toUIText(),
    //             temp = "30".toUIText(),
    //             uvi = "null".toUIText(),
    //             visibility = "null".toUIText(),
    //             weather = CurrentWeatherUIState.Weather(
    //                 description = "Cloudy with chances of rain".toUIText(),
    //                 icon = R.drawable.profile_icon.toImageHolder(),
    //                 main = "Cloudy".toUIText(),
    //             ),
    //             windDeg = "null".toUIText(),
    //             windSpeed = "58".toUIText()
    //
    //         )
    //     )
    // )
}