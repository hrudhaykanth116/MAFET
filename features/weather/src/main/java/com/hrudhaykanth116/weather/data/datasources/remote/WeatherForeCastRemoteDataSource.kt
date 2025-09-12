package com.hrudhaykanth116.weather.data.datasources.remote

import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.OpenWeatherApiService
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import javax.inject.Inject

class WeatherForeCastRemoteDataSource @Inject constructor(
    private val openWeatherApiService: OpenWeatherApiService
): NetworkDataSource() {

    suspend fun getWeatherForeCast(latitude: String, longitude: String): ApiResultWrapper<WeatherForeCastResponse> = getResult{
        openWeatherApiService.getDailyWeatherForeCast(latitude, longitude)
    }


}