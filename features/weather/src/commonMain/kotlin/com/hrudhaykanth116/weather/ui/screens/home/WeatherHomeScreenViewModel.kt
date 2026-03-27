package com.hrudhaykanth116.weather.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.UserMessage
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEffect
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastFromLatLongUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastUseCaseFromLatLongUseCase
import com.hrudhaykanth116.weather.location.LocationService
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherHomeScreenViewModel(
    private val getForeCastUseCaseFromLatLongUseCase: GetForeCastUseCaseFromLatLongUseCase,
    private val getForeCastFromLatLongUseCase: GetForeCastFromLatLongUseCase,
    private val locationService: LocationService,
    networkMonitor: NetworkMonitor,
) : UIStateViewModel<WeatherHomeScreenUIState, WeatherHomeScreenEvent, WeatherHomeScreenEffect>(
    initialState = UIState.Idle(),
    defaultState = WeatherHomeScreenUIState(),
    networkMonitor = networkMonitor,
) {

    private var getForeCastJob: Job? = null

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

            val locationResult = locationService.getCurrentLocation()

            if (locationResult != null) {
                Logger.d(TAG, "fetchLocationAndWeather: lat=${locationResult.latitude}, lon=${locationResult.longitude}")

                getForeCastJob?.cancel()

                val addressName = locationService.getAddressFromCoordinates(
                    locationResult.latitude,
                    locationResult.longitude
                ) ?: "Unknown"

                setLoadingState(
                    currentContentState?.copy(errorState = null),
                    "Fetching forecast for $addressName".toUIText()
                )

                val foreCastDataResult = getForeCastFromLatLongUseCase(
                    locationResult.latitude,
                    locationResult.longitude
                )

                when (foreCastDataResult) {
                    is RepoResultWrapper.Error -> {
                        setState {
                            UIState.Idle(
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
                handleLocationOrGpsUnAvailableCases()
            }
        }
    }

    fun fetchData(location: String?) {
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

            val foreCastDataResult = getForeCastUseCaseFromLatLongUseCase(location)

            when (foreCastDataResult) {
                is RepoResultWrapper.Error -> {
                    Logger.e(TAG, "fetchData: foreCastDataResult: ${foreCastDataResult.errorState}")
                    setState {
                        UIState.Idle(
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
        val lastKnownLocation = "Hyderabad"
        fetchData(lastKnownLocation)
    }

    override fun initializeData() {
        // fetch data needs to be called after checking location permissions
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
                setState {
                    UIState.Idle(
                        contentStateOrDefault.copy(
                            isSearchActive = false,
                        )
                    )
                }
            }

            WeatherHomeScreenEvent.GpsIconClicked -> {
                fetchLocationAndWeather()
            }
        }
    }

    companion object {
        private const val TAG = "WeatherHomeScreenVM"
    }

}
