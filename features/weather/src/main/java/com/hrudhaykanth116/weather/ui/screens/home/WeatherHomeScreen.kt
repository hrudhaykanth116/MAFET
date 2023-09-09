package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState

@Composable
fun WeatherHomeScreen(
    modifier: Modifier = Modifier,
    todoListViewModel: WeatherHomeScreenViewModel = hiltViewModel(),
) {

    val uiState: State<WeatherHomeScreenUIState> =
        todoListViewModel.stateFlow.collectAsStateWithLifecycle()

    WeatherHomeScreenUI(
        uiState.value,
        weatherHomeScreenCallbacks = WeatherHomeScreenCallbacks(
            onLocationTextChanged = {
                todoListViewModel.processEvent(WeatherHomeScreenEvent.OnLocationTextChanged(it))
            },
            search = {
                todoListViewModel.processEvent(WeatherHomeScreenEvent.Search)
            }
        ),
        modifier = modifier,
    )

}