package com.hrudhaykanth116.weather.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEffect
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeScreenViewModel @Inject constructor(
    private val getForeCastUseCase: GetForeCastUseCase,
) : UDFViewModel<WeatherHomeScreenUIState, WeatherHomeScreenEvent, WeatherHomeScreenEffect>(
    WeatherHomeScreenUIState()
) {

    private var job: Job? = null

    init {
        fetchData()
        // viewModelScope.launch {
        //     do {
        //         fetchData()
        //         delay(TimeUnit.MINUTES.toMillis(10))
        //     }while (isActive)
        // }
    }

    private fun fetchData() {
        job?.cancel()

        val location = state.location
        if (location.isBlank()) {
            setState {
                copy(
                    errorMessage = UserMessage.Error(UIText.Text("Please enter location name"))
                )
            }
            return
        }

        job = viewModelScope.launch {

            setState {
                copy(
                    isLoading = true
                )
            }

            val foreCastDataResult = getForeCastUseCase(
                location,
            )

            when (foreCastDataResult) {
                is DataResult.Error -> {
                    setState {
                        copy(
                            errorMessage = foreCastDataResult.uiMessage?.let { UserMessage.Error(it) },
                            isLoading = false,
                        )
                    }
                }

                is DataResult.Success -> {

                    setState {
                        copy(
                            todayWeatherUIState = foreCastDataResult.data.first,
                            weatherForeCastListItemsUIState = foreCastDataResult.data.second,
                            isLoading = false,
                        )
                    }
                }
            }

        }

    }

    override fun processEvent(event: WeatherHomeScreenEvent) {
        when (event) {
            is WeatherHomeScreenEvent.Refresh -> {
                fetchData()
            }

            is WeatherHomeScreenEvent.UserMessageShown -> setState {
                copy(
                    errorMessage = null
                )
            }

            is WeatherHomeScreenEvent.OnLocationTextChanged -> setState {
                copy(
                    location = event.newLocationText
                )
            }
            WeatherHomeScreenEvent.Search -> fetchData()
        }
    }

}