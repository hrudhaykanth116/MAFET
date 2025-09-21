package com.hrudhaykanth116.weather.ui.screens.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.core.common.utils.gps.GpsUtils
import com.hrudhaykanth116.core.common.utils.gps.GpsUtils.isGpsEnabled
import com.hrudhaykanth116.core.common.utils.gps.GpsUtils.requestEnableGps
import com.hrudhaykanth116.core.ui.components.AppScreen
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent

private const val TAG = "WeatherHomeScreen"

@Composable
fun WeatherHomeScreen(
    modifier: Modifier = Modifier,
    weatherHomeScreenViewModel: WeatherHomeScreenViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val enableGpsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (GpsUtils.isGpsEnabled(context)) {
            weatherHomeScreenViewModel.fetchLocationAndWeather()
        }else{
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
        when (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            PermissionChecker.PERMISSION_GRANTED -> {
                if (!isGpsEnabled(context)) {
                    requestEnableGps(context) { intentSenderRequest ->
                        enableGpsLauncher.launch(intentSenderRequest)
                    }
                } else {
                    weatherHomeScreenViewModel.fetchLocationAndWeather()
                }
            }
            else -> locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    AppScreen(
        weatherHomeScreenViewModel
    ) { state ->

        WeatherHomeScreenUI(
            state,
            weatherHomeScreenCallbacks = WeatherHomeScreenCallbacks(
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
                    weatherHomeScreenViewModel.processEvent(WeatherHomeScreenEvent.OnExpandedChange(it)
                    )
                },
                onGpsIconClicked = {
                    weatherHomeScreenViewModel.processEvent(WeatherHomeScreenEvent.GpsIconClicked)
                }
            ),
            modifier = modifier,
        )

    }


}