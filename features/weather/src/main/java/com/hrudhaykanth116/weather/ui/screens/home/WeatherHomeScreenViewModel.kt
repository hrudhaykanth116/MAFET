package com.hrudhaykanth116.weather.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.weathertens.common.data.models.DataResult
import com.hrudhaykanth116.weathertens.common.data.models.UIText
import com.hrudhaykanth116.weathertens.features.auth.data.repository.user.IUserRepository
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
    private val userRepository: IUserRepository,
    private val firebaseAuth: FirebaseAuth,
) : UDFViewModel<WeatherHomeScreenUIState, WeatherHomeScreenEvent, WeatherHomeScreenEffect>(
    WeatherHomeScreenUIState()
) {

    private var job: Job? = null

    init {
        fetchUserData()
        fetchData("Kolkata")
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            when (val userDataResult = userRepository.getUserData()) {
                is DataResult.Error -> {
                    setState {
                        copy(
                            errorMessage = userDataResult.uiMessage
                        )
                    }
                }
                is DataResult.Success -> {
                    val userData = userDataResult.data
                    setState {
                        copy(
                            userName = userData.userName,
                            userBio = userData.bio,
                            userProfileImageUrl = userData.profileImgUrl,
                        )
                    }
                }
            }
        }
    }

    private fun fetchData(location: String?) {
        job?.cancel()

        if(location.isNullOrBlank()){
            setState {
                copy(
                    errorMessage = UIText.Text("Please enter location name")
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
                            errorMessage = foreCastDataResult.uiMessage,
                            isLoading = false,
                        )
                    }
                }
                is DataResult.Success -> {
                    setState {
                        copy(
                            weatherListItemsUIState = foreCastDataResult.data,
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
                fetchData(event.location)
            }
            WeatherHomeScreenEvent.LogOut -> {
                firebaseAuth.signOut()
                setState {
                    copy(
                        isLoggedOut = true,
                    )
                }
            }
            is WeatherHomeScreenEvent.UserMessageShown -> setState {
                copy(
                    errorMessage = null
                )
            }
        }
    }

}