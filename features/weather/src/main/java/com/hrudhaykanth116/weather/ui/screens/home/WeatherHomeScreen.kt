package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState

@Composable
fun WeatherHomeScreen(
    modifier: Modifier = Modifier,
    todoListViewModel: WeatherHomeScreenViewModel = hiltViewModel(),
) {

    val uiState: State<WeatherHomeScreenUIState> =
        todoListViewModel.stateFlow.collectAsStateWithLifecycle()

    WeatherHomeScreenUI(
        uiState.value
    )

}