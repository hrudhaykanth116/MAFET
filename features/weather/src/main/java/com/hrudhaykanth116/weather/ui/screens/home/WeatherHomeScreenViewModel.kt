package com.hrudhaykanth116.weather.ui.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.hrudhaykanth116.core.ui.models.UserMessage
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEffect
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastFromLatLongUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastUseCaseFromLatLongUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale

class WeatherHomeScreenViewModel(
    private val getForeCastUseCaseFromLatLongUseCase: GetForeCastUseCaseFromLatLongUseCase,
    private val getForeCastFromLatLongUseCase: GetForeCastFromLatLongUseCase,
    private val context: Context,
    networkMonitor: NetworkMonitor,
) : UIStateViewModel<WeatherHomeScreenUIState, WeatherHomeScreenEvent, WeatherHomeScreenEffect>(
    initialState = UIState.Idle(),
    defaultState = WeatherHomeScreenUIState(),
    networkMonitor = networkMonitor,
) {

    private var getForeCastJob: Job? = null

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    init {
        initializeData()
    }

    fun fetchLocationAndWeather() {

        viewModelScope.launch {

            setState {
                UIState.Loading(
                    currentContentState?.copy(errorState = null),
                    message = "Fetching location...".toUIText()
                )
            }

            val location: Location? = getCurrentLocation(fusedLocationClient)

            Logger.d(TAG, "fetchLocationAndAddress: $location")
            if (location != null) {

                getForeCastJob?.cancel()

                val addressName: String? = getAddressFromLocation(location)

                setLoadingState(
                    currentContentState?.copy(errorState = null),
                    "Fetching forecast for $addressName".toUIText()
                )
                val foreCastDataResult = getForeCastFromLatLongUseCase(
                    location.latitude, location.longitude
                )

                when (foreCastDataResult) {
                    is RepoResultWrapper.Error -> {
                        setState {
                            UIState.Idle(
                                // When there is error, we have to preserve only address name entered. Reset should reset
                                contentState = defaultState.copy(
                                    errorState = foreCastDataResult.errorState,
                                    location = addressName,
                                )
                            )
                        }
                    }

                    is RepoResultWrapper.Success -> {
                        setState {
                            UIState.Idle(
                                contentStateOrDefault.copy(
                                    todayWeatherUIState = foreCastDataResult.data.first,
                                    weatherForeCastListItemsUIState = foreCastDataResult.data.second,
                                    location = addressName,
                                )
                            )
                        }
                    }
                }

            } else {
                setState {
                    UIState.Idle(
                        contentStateOrDefault.copy(
                            locationError = "Unable to fetch location".toUIText()
                        )
                    )
                }
            }
        }
    }

    private fun fetchData(location: String?) {
        Logger.d(TAG, "fetchData: location: $location")
        getForeCastJob?.cancel()

        if (location.isNullOrBlank()) {
            setState {
                UIState.Idle(
                    contentStateOrDefault,
                    userMessage = UserMessage.Error("Please enter a valid location".toUIText())
                )
            }
            return
        }

        getForeCastJob = viewModelScope.launch {

            setLoadingState(
                currentContentState?.copy(isSearchActive = false, errorState = null),
                "Fetching forecast for $location".toUIText()
            )


            val foreCastDataResult = getForeCastUseCaseFromLatLongUseCase(
                location,
            )

            when (foreCastDataResult) {
                is RepoResultWrapper.Error -> {
                    Logger.e(TAG, "fetchData: foreCastDataResult: ${foreCastDataResult.errorState}")
                    setState {
                        UIState.Idle(
                            // When there is error, we have to preserve only address name entered. Reset should reset
                            contentState = defaultState.copy(
                                errorState = foreCastDataResult.errorState,
                                location = location,
                            ),
                        )
                    }
                }

                is RepoResultWrapper.Success -> {
                    Logger.d(TAG, "fetchData: success")

                    setState {
                        UIState.Idle(
                            contentStateOrDefault.copy(
                                location = location,
                                todayWeatherUIState = foreCastDataResult.data.first,
                                weatherForeCastListItemsUIState = foreCastDataResult.data.second,
                            )
                        )
                    }
                }
            }

        }

    }


    fun handleLocationOrGpsUnAvailableCases() {
        // TODO: weather implement last known location.
        val lastKnownLocation = "Hyderabad"
        fetchData(lastKnownLocation)

    }

    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocation(
        fusedLocationClient: FusedLocationProviderClient,
    ): Location? = suspendCancellableCoroutine { cont ->
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                cont.resume(location, null)
            }
            .addOnFailureListener {
                cont.resume(null, null)
            }
    }

    private fun getAddressFromLocation(
        location: Location,
    ): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val latitude = location.latitude
        val longitude = location.longitude

        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            Logger.d(TAG, "getAddressFromLocation: $addresses")
            if (addresses != null) {
                if (addresses.isNotEmpty()) {

                    val address = addresses[0]

                    val addressLine = address.getAddressLine(0)
                    val city = addresses[0].locality
                    val state = addresses[0].adminArea
                    val country = addresses[0].countryName
                    val postalCode = addresses[0].postalCode
                    val knownName = addresses[0].featureName

                    return city
                } else {
                    return null
                }
            } else {
                return null
            }
        } catch (e: Exception) {
            Logger.e(TAG, "getAddressFromLocation: ", e)
            return null
        }
    }

    override fun initializeData() {
        // fetch data need to be called only after gps and location permissions checks.
        // fetchData(contentStateOrDefault.location)
    }

    override fun processEvent(event: WeatherHomeScreenEvent) {

        Logger.d(TAG, "processEvent: $event")

        when (event) {
            is WeatherHomeScreenEvent.Refresh -> {
                fetchData(contentStateOrDefault.location)
            }

            is WeatherHomeScreenEvent.UserMessageShown -> setState {
                UIState.Idle(
                    contentStateOrDefault,
                    userMessage = null,
                )
            }

            is WeatherHomeScreenEvent.OnLocationTextChanged -> setState {
                UIState.Idle(
                    contentStateOrDefault.copy(
                        searchText = event.newLocationText
                    )
                )
            }

            WeatherHomeScreenEvent.Search -> {
                val searchLocation: String? = contentStateOrDefault.searchText

                setState {
                    UIState.Idle(
                        contentStateOrDefault.copy(
                            location = searchLocation,
                            isSearchActive = false,
                        )
                    )
                }

                fetchData(searchLocation)
            }

            WeatherHomeScreenEvent.OnSearchIconClicked -> {
                setState {
                    UIState.Idle(
                        contentStateOrDefault.copy(
                            isSearchActive = true,
                        )
                    )
                }
            }

            is WeatherHomeScreenEvent.OnExpandedChange -> {
                setState {
                    UIState.Idle(
                        contentStateOrDefault.copy(
                            isSearchActive = event.isExpanded,
                        )
                    )
                }
            }

            WeatherHomeScreenEvent.OnSearchCancelled -> {

                val searchLocation: String? = contentStateOrDefault.location

                setState {
                    UIState.Idle(
                        contentStateOrDefault.copy(
                            isSearchActive = false,
                        )
                    )
                }
            }

            WeatherHomeScreenEvent.GpsIconClicked -> {
                // hrudhay_check_list: check permissions and location state
                fetchLocationAndWeather()
            }
        }
    }

    companion object {
        private const val TAG = "WeatherHomeScreenViewMo"
    }

}