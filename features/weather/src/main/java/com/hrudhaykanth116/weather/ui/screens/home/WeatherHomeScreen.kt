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
import com.hrudhaykanth116.weather.ui.widgets.HourlyView
import org.koin.androidx.compose.koinViewModel

private const val TAG = "WeatherHomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    modifier: Modifier = Modifier,
    weatherHomeScreenViewModel: WeatherHomeScreenViewModel = koinViewModel(),
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
        onUserMessageShown = { it ->
            weatherHomeScreenViewModel.onUserMessageShown(it)
        },
    )


}