package com.hrudhaykanth116.weather.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.compose.modifier.screenBackground
import com.hrudhaykanth116.core.ui.components.ApiErrorScreen
import com.hrudhaykanth116.core.ui.components.AppProgressBar
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.weather.domain.models.TodayWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState
import com.hrudhaykanth116.weather.ui.widgets.HourlyView

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WeatherHomeScreenUI(
    modifier: Modifier,
    uiState: UIState<WeatherHomeScreenUIState>,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks,
    onRetry: () -> Unit,
    onUserMessageShown: (UIState.Idle<WeatherHomeScreenUIState>) -> Unit,
) {

    val context = LocalContext.current

    val state = uiState.contentState ?: WeatherHomeScreenUIState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .screenBackground()
    ) {
        BottomSheetScaffold(
            modifier = Modifier,
            containerColor = Color.Transparent,
            sheetContainerColor = Color.White,
            sheetContent = {
                if (uiState is UIState.Idle) {

                    val state = uiState.contentState ?: return@BottomSheetScaffold

                    if (!state.isSearchActive) {
                        WeatherHomeBottomSheet(
                            state.weatherForeCastListItemsUIState
                        )
                    }
                } else {
                    // Box(modifier = Modifier.height(1.dp)) {} // Empty Box to prevent crash
                }
            },
            sheetPeekHeight = if (!state.isSearchActive && state.errorState == null && uiState is UIState.Idle) 100.dp else 0.dp, // Control visibility
        ) {
            Content(state, it, weatherHomeScreenCallbacks, onRetry, uiState)
        }

        if (uiState is UIState.Loading) {
            AppProgressBar(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = if (uiState.contentState == null) Color(
                            0xFF040404
                        ) else Color.Transparent
                    ),
                message = uiState.message
            )
        }

        if (uiState is UIState.Idle) {
            when (val userMessage = uiState.userMessage) {
                is UserMessage.Error -> userMessage.message.getText(context)
                is UserMessage.Success -> userMessage.message.getText(context)
                is UserMessage.Warning -> userMessage.message.getText(context)
                else -> null
            }?.let { message: String ->

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                onUserMessageShown(uiState)
            }
        }
    }
}

@Composable
private fun Content(
    state: WeatherHomeScreenUIState,
    values: PaddingValues,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks,
    onRetry: () -> Unit,
    uiState: UIState<WeatherHomeScreenUIState>,
) {
    // hrudhay_check_list: Handle this case Loading.
    val weather = state.todayWeatherUIState ?: TodayWeatherUIState()

    Column(
        modifier = Modifier
            .padding(values)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(uiState !is UIState.Loading){
            WeatherHomeTopBar(
                state.searchText ?: "",
                location = state.location,
                state.isSearchActive,
                weatherHomeScreenCallbacks,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if(!state.isSearchActive){
            VerticalSpacer()

            if (state.errorState != null) {
                ApiErrorScreen(
                    onRetry = onRetry,
                    apiError = state.errorState,
                    modifier = Modifier
                        .fillMaxSize(),
                )
            } else if (uiState is UIState.Idle) {
                TodayWeatherElements(
                    state.todayWeatherUIState?.weatherElementUIState,
                    weather.weatherMain,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                VerticalSpacer()
                HourlyView(
                    state.todayWeatherUIState?.weatherHourlyList,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@MyPreview
@Composable
fun WeatherHomeScreenUIPreview(
    @PreviewParameter(WeatherHomeScreenUIStateProvider::class, limit = 1)
    uiState: UIState<WeatherHomeScreenUIState>
) {
    AppPreviewContainer {
        WeatherHomeScreenUI(
            modifier = Modifier.background(color = Color.Gray),
            uiState = uiState,
            weatherHomeScreenCallbacks = WeatherHomeScreenCallbacks(),
            onRetry = {},
            onUserMessageShown = {}
        )
    }
}