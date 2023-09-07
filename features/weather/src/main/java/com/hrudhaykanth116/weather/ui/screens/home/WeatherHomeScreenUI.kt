package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.compose.modifier.largeRadialBackground
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.weather.R
import com.hrudhaykanth116.weather.domain.models.CurrentWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState

@Composable
fun WeatherHomeScreenUI(
    uiState: WeatherHomeScreenUIState,
    modifier: Modifier = Modifier,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks = WeatherHomeScreenCallbacks(),
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WeatherHomeTopBar(
                uiState.location
            )
        },
    ) {
        ContentContainer(
            uiState,
            weatherHomeScreenCallbacks = weatherHomeScreenCallbacks,
            modifier = Modifier.padding(it),
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentContainer(
    state: WeatherHomeScreenUIState,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier.fillMaxSize()) {

        WeatherHomeSearchBar(
            state.location,
            onTextChange = weatherHomeScreenCallbacks.onLocationTextChanged,
            onSearch = weatherHomeScreenCallbacks.search
        )

        VerticalSpacer()
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val weather = state.currentWeatherUIState?.weather ?: return@AppCard
            val current = state.currentWeatherUIState
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .largeRadialBackground(
                        listOf(Color(0xFF2be4dc), Color(0xFF243484))
                    ),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                AppIcon(
                    imageHolder = weather.icon,
                    uiText = weather.main,
                    iconModifier = Modifier.size(150.dp)
                )
                VerticalSpacer()
                Row {
                    AppIcon(
                        imageHolder = R.drawable.profile_icon.toImageHolder(),
                        uiText = current.temp, iconModifier = Modifier.size(70.dp)
                    )
                    AppIcon(
                        imageHolder = R.drawable.profile_icon.toImageHolder(),
                        uiText = current.clouds, iconModifier = Modifier.size(70.dp)
                    )
                    AppIcon(
                        imageHolder = R.drawable.profile_icon.toImageHolder(),
                        uiText = current.windSpeed, iconModifier = Modifier.size(70.dp)
                    )
                }
            }
        }



    }


}

@Composable
fun WeatherHomeSearchBar(
    text: String,
    onTextChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
) {
    AppSearchBar(
        text = text,
        onTextChange = onTextChange,
        onSearch = onSearch,
    )
}

@MyPreview
@Composable
fun WeatherHomeScreenUIPreview(

) {
    WeatherHomeScreenUI(
        uiState = WeatherHomeScreenUIState(
            currentWeatherUIState = CurrentWeatherUIState(
                clouds = "51".toUIText(),
                dewPoint = "null".toUIText(),
                dt = "null".toUIText(),
                feelsLike = "null".toUIText(),
                humidity = "null".toUIText(),
                pressure = "null".toUIText(),
                sunrise = "null".toUIText(),
                sunset = "null".toUIText(),
                temp = "30".toUIText(),
                uvi = "null".toUIText(),
                visibility = "null".toUIText(),
                weather = CurrentWeatherUIState.Weather(
                    description = "Cloudy with chances of rain".toUIText(),
                    icon = R.drawable.profile_icon.toImageHolder(),
                    id = "null".toUIText(),
                    main = "Cloudy".toUIText(),
                ),
                windDeg = "null".toUIText(),
                windSpeed = "58".toUIText()

            )
        )
    )
}