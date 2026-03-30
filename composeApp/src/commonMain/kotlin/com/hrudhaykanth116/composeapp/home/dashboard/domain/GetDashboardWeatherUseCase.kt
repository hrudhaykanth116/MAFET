package com.hrudhaykanth116.composeapp.home.dashboard.domain

import com.hrudhaykanth116.composeapp.home.dashboard.models.WeatherSummary
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastFromLatLongUseCase
import com.hrudhaykanth116.weather.location.LocationService

class GetDashboardWeatherUseCase(
    private val locationService: LocationService,
    private val getForeCastFromLatLongUseCase: GetForeCastFromLatLongUseCase
) {

    suspend operator fun invoke(): WeatherSummary? {
        val location = locationService.getCurrentLocation() ?: return null

        val addressName = locationService.getAddressFromCoordinates(
            location.latitude,
            location.longitude
        )

        val result = getForeCastFromLatLongUseCase(
            latitude = location.latitude,
            longitude = location.longitude
        )

        return when (result) {
            is DomainResult.Error -> null
            is DomainResult.Success -> {
                val todayWeather = result.data.first
                val weatherMain = todayWeather.weatherMain ?: return null

                WeatherSummary(
                    temperature = weatherMain.title,
                    condition = weatherMain.description,
                    location = addressName
                )
            }
        }
    }
}
