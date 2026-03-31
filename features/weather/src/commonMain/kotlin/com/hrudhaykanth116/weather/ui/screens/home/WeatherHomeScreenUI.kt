package com.hrudhaykanth116.weather.ui.screens.home

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.ui.components.ApiErrorScreen
import com.hrudhaykanth116.core.ui.components.AppProgressBar
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.modifier.screenBackground
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
    dateTimeUtils: DateTimeUtils,
    onRetry: () -> Unit,
    onUserMessageShown: (UIState.Idle<WeatherHomeScreenUIState>) -> Unit,
) {
    val state = uiState.contentState ?: WeatherHomeScreenUIState()

    val formattedTimestamp = remember(state.lastFetchedTimestamp) {
        state.lastFetchedTimestamp?.let {
            dateTimeUtils.getFormattedDateTime(it, DateTimeUtils.COMPLETE_DATE_TIME_FORMAT)
        }
    }

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
                    val contentState = uiState.contentState ?: return@BottomSheetScaffold

                    if (!contentState.isSearchActive) {
                        WeatherHomeBottomSheet(
                            contentState.weatherForeCastListItemsUIState
                        )
                    }
                }
            },
            sheetPeekHeight = if (!state.isSearchActive && state.domainError == null && uiState is UIState.Idle) 100.dp else 0.dp,
        ) {
            Content(state, it, weatherHomeScreenCallbacks, dateTimeUtils, formattedTimestamp, onRetry, uiState)
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
    }
}

@Composable
private fun Content(
    state: WeatherHomeScreenUIState,
    values: PaddingValues,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks,
    dateTimeUtils: DateTimeUtils,
    formattedTimestamp: String?,
    onRetry: () -> Unit,
    uiState: UIState<WeatherHomeScreenUIState>,
) {
    val weather = state.todayWeatherUIState ?: TodayWeatherUIState()

    Column(
        modifier = Modifier
            .padding(values)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState !is UIState.Loading) {
            WeatherHomeTopBar(
                searchText = state.searchText ?: "",
                location = state.location,
                locationSource = state.locationSource,
                lastFetchedTimestamp = state.lastFetchedTimestamp,
                formattedTimestamp = formattedTimestamp,
                isSearchActive = state.isSearchActive,
                weatherHomeScreenCallbacks = weatherHomeScreenCallbacks,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (!state.isSearchActive) {
            VerticalSpacer()

            if (state.domainError != null) {
                ApiErrorScreen(
                    onRetry = onRetry,
                    domainError = state.domainError,
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
