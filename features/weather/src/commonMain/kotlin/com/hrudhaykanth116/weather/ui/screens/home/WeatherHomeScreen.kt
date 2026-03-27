package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    modifier: Modifier = Modifier,
    weatherHomeScreenViewModel: WeatherHomeScreenViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        weatherHomeScreenViewModel.fetchData(null)
    }

    val weatherHomeScreenCallbacks = WeatherHomeScreenCallbacks(
        onLocationTextChanged = {
            weatherHomeScreenViewModel.processEvent(WeatherHomeScreenEvent.OnLocationTextChanged(it))
        },
        search = {
            weatherHomeScreenViewModel.processEvent(WeatherHomeScreenEvent.Search)
        },
        onSearchCancelled = {
            weatherHomeScreenViewModel.processEvent(WeatherHomeScreenEvent.OnSearchCancelled)
        },
        onExpandedChange = {
            weatherHomeScreenViewModel.processEvent(
                WeatherHomeScreenEvent.OnExpandedChange(it)
            )
        },
        onGpsIconClicked = {
            weatherHomeScreenViewModel.processEvent(WeatherHomeScreenEvent.GpsIconClicked)
        },
        onSearchIconClicked = {
            weatherHomeScreenViewModel.processEvent(WeatherHomeScreenEvent.OnSearchIconClicked)
        },
        onRefreshIconClicked = {
            weatherHomeScreenViewModel.processEvent(WeatherHomeScreenEvent.Refresh)
        }
    )

    val uiState: UIState<WeatherHomeScreenUIState> by weatherHomeScreenViewModel.uiStateFlow.collectAsStateWithLifecycle()

    WeatherHomeScreenUI(
        modifier,
        uiState,
        weatherHomeScreenCallbacks,
        onRetry = {
            weatherHomeScreenViewModel.onRetry()
        },
        onUserMessageShown = {
            weatherHomeScreenViewModel.onUserMessageShown(it)
        },
    )
}
