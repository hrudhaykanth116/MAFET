package com.hrudhaykanth116.weather.ui.screens.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState

private const val TAG = "WeatherHomeScreen"

@Composable
fun WeatherHomeScreen(
    modifier: Modifier = Modifier,
    weatherHomeScreenViewModel: WeatherHomeScreenViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val state: State<WeatherHomeScreenUIState> =
        weatherHomeScreenViewModel.stateFlow.collectAsStateWithLifecycle()


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
                Logger.d(TAG, "WeatherHomeScreen: ")
                weatherHomeScreenViewModel.fetchLocationAndWeather()
            }
            else -> locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    Box(modifier = modifier.fillMaxSize()) {
        if (state.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }else if(state.value.errorMessage != null){
            AppText(
                uiText = "Something went wrong. ".toUIText(),
                modifier = Modifier.align(Alignment.Center)
            )
        }else{
            WeatherHomeScreenUI(
                state.value,
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


}