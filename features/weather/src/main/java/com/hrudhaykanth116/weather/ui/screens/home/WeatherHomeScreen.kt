package com.hrudhaykanth116.weather.ui.screens.home

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.common.utils.compose.modifier.screenBackground
import com.hrudhaykanth116.core.common.utils.gps.GpsUtils.isGpsEnabled
import com.hrudhaykanth116.core.common.utils.gps.GpsUtils.requestEnableGps
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.components.ApiErrorScreen
import com.hrudhaykanth116.core.ui.components.AppProgressBar
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.weather.domain.models.TodayWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState

private const val TAG = "WeatherHomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    modifier: Modifier = Modifier,
    weatherHomeScreenViewModel: WeatherHomeScreenViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val enableGpsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (isGpsEnabled(context)) {
            weatherHomeScreenViewModel.fetchLocationAndWeather()
        } else {
            weatherHomeScreenViewModel.handleLocationOrGpsUnAvailableCases()
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            weatherHomeScreenViewModel.fetchLocationAndWeather()
        } else {
            // address = "Permission denied"
        }
    }

    LaunchedEffect(Unit) {
        when (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )) {
            PermissionChecker.PERMISSION_GRANTED -> {
                if (!isGpsEnabled(context)) {
                    requestEnableGps(context) { intentSenderRequest ->
                        enableGpsLauncher.launch(intentSenderRequest)
                    }
                } else {
                    weatherHomeScreenViewModel.fetchLocationAndWeather()
                }
            }

            else -> locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
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
        }
    )

    val uiState: UIState<WeatherHomeScreenUIState> by weatherHomeScreenViewModel.uiStateFlow.collectAsStateWithLifecycle()

    Temp(
        modifier,
        uiState,
        weatherHomeScreenCallbacks,
        onRetry = {
            weatherHomeScreenViewModel.onRetry()
        },
        onUserMessageShown = { it ->
            weatherHomeScreenViewModel.onUserMessageShown(it)
        },
    )


}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Temp(
    modifier: Modifier,
    uiState: UIState<WeatherHomeScreenUIState>,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks,
    onRetry: () -> Unit,
    onUserMessageShown: (UIState.Idle<WeatherHomeScreenUIState>) -> Unit,
) {

    Logger.d(TAG, "Temp: $uiState")

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

            // hrudhay_check_list: Handle this case Loading.
            val weather = state.todayWeatherUIState ?: TodayWeatherUIState()

            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WeatherHomeTopBar(
                    state.searchText ?: "",
                    state.isSearchActive,
                    weatherHomeScreenCallbacks,
                    modifier = Modifier.fillMaxWidth()
                )

                VerticalSpacer()

                if (state.errorState != null) {
                    ApiErrorScreen(
                        onRetry = onRetry,
                        apiError = state.errorState,
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                } else if(uiState is UIState.Idle) {
                    TodayWeatherElements(
                        state.todayWeatherUIState?.weatherElementUIState,
                        weather.weatherMain,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier
                            // .weight(1f)
                            .height(8.dp)
                    )
                    HourlyView(
                        state.todayWeatherUIState?.weatherHourlyList,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        if (uiState is UIState.Loading) {
            AppProgressBar(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = if (uiState.contentState == null) Color(
                            0xFF040404
                        ) else Color.Transparent
                    )
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