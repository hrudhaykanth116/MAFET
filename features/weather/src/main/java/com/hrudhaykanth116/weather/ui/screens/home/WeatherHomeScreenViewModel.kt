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
import com.hrudhaykanth116.core.common.mappers.mapToUIMessage
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEffect
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastFromLatLongUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherHomeScreenViewModel @Inject constructor(
    private val getForeCastUseCase: GetForeCastUseCase,
    private val getForeCastFromLatLongUseCase: GetForeCastFromLatLongUseCase,
    @ApplicationContext private val context: Context,
    private val networkMonitor: NetworkMonitor,
) : UIStateViewModel<WeatherHomeScreenUIState, WeatherHomeScreenEvent, WeatherHomeScreenEffect>(
    initialState = UIState.Idle(),
    defaultState = WeatherHomeScreenUIState(),
    networkMonitor = networkMonitor,
) {

    private var job: Job? = null

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    fun fetchLocationAndWeather() {

        setState {
            UIState.Loading(currentContentState)
        }

        viewModelScope.launch {
            val location = getCurrentLocation(fusedLocationClient)

            Logger.d(TAG, "fetchLocationAndAddress: $location")
            if (location != null) {

                fetchData(location.latitude, location.longitude)

                val addressName: String? = getAddressFromLocation(location)

                onAddressFetched(addressName)
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

    private fun fetchData(location: String) {
        Logger.d(TAG, "fetchData: location: $location")
        job?.cancel()

        if (location.isBlank()) {
            setState {
                UIState.Idle(
                    contentStateOrDefault.copy(
                        errorMessage = UserMessage.Error(UIText.Text("Please enter location name"))
                    )
                )
            }
            return
        }

        job = viewModelScope.launch {

            setState {
                UIState.Loading(
                    contentStateOrDefault.copy(
                        isLoading = true,
                        isSearchActive = false,
                    )
                )
            }

            val foreCastDataResult = getForeCastUseCase(
                location,
            )

            when (foreCastDataResult) {
                is RepoResultWrapper.Error -> {
                    Logger.e(TAG, "fetchData: foreCastDataResult: ${foreCastDataResult.errorState}")
                    setState {
                        UIState.Error(
                            errorState = foreCastDataResult.errorState,
                        )
                    }
                }

                is RepoResultWrapper.Success -> {
                    Logger.d(TAG, "fetchData: success")

                    setState {
                        UIState.Idle(
                            contentStateOrDefault.copy(
                                todayWeatherUIState = foreCastDataResult.data.first,
                                weatherForeCastListItemsUIState = foreCastDataResult.data.second,
                                isLoading = false,
                            )
                        )
                    }
                }
            }

        }

    }

    private fun fetchData(
        latitude: Double,
        longitude: Double,
    ) {
        job?.cancel()

        job = viewModelScope.launch {

            setState {
                UIState.Loading(currentContentState)
            }

            val foreCastDataResult = getForeCastFromLatLongUseCase(
                latitude, longitude
            )

            when (foreCastDataResult) {
                is RepoResultWrapper.Error -> {
                    setState {
                        UIState.Error(
                            errorState = foreCastDataResult.errorState,
                        )
                    }
                }

                is RepoResultWrapper.Success -> {

                    setState {
                        UIState.Idle(
                            contentStateOrDefault.copy(
                                todayWeatherUIState = foreCastDataResult.data.first,
                                weatherForeCastListItemsUIState = foreCastDataResult.data.second,
                                isLoading = false,
                            )
                        )
                    }
                }
            }

        }

    }

    fun handleLocationOrGpsUnAvailableCases() {

        val lastKnownLocation = "Hyderabad"
        onAddressFetched(lastKnownLocation)
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

    private fun onAddressFetched(address: String?) {
        Logger.d(TAG, "onAddressFetched: ")
        setState {
            UIState.Idle(
                contentStateOrDefault.copy(
                    location = address ?: "NA"
                )
            )
        }
        address?.let { fetchData(it) }
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

    }

    override fun processEvent(event: WeatherHomeScreenEvent) {
        when (event) {
            is WeatherHomeScreenEvent.Refresh -> {
                fetchData(contentStateOrDefault.location)
            }

            is WeatherHomeScreenEvent.UserMessageShown -> setState {
                UIState.Idle(
                    contentStateOrDefault.copy(
                        errorMessage = null
                    )
                )
            }

            is WeatherHomeScreenEvent.OnLocationTextChanged -> setState {
                UIState.Idle(
                    contentStateOrDefault.copy(
                        location = event.newLocationText
                    )
                )
            }

            WeatherHomeScreenEvent.Search -> fetchData(contentStateOrDefault.location)

            is WeatherHomeScreenEvent.OnExpandedChange -> {
                setState {
                    UIState.Idle(
                        contentStateOrDefault.copy(
                            isSearchActive = event.isExpanded
                        )
                    )
                }
            }

            WeatherHomeScreenEvent.OnSearchCancelled -> {
                setState {
                    UIState.Idle(
                        contentStateOrDefault.copy(
                            isSearchActive = false
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