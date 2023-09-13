package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.common.utils.ui.ToastHelper
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState

@Composable
fun WeatherHomeScreen(
    modifier: Modifier = Modifier,
    todoListViewModel: WeatherHomeScreenViewModel = hiltViewModel(),
) {

    val state: State<WeatherHomeScreenUIState> =
        todoListViewModel.stateFlow.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        if (state.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }else if(state.value.errorMessage != null){
            // TODO: Consider showing toast msg or a more illustrative image and error message
            AppText(
                uiText = "Something went wrong. ".toUIText(),
                modifier = Modifier.align(Alignment.Center)
            )
        }else{
            WeatherHomeScreenUI(
                state.value,
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
    }


}