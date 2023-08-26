package com.hrudhaykanth116.weather.data.datasources.remote

import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.WeatherForeCastApiService
import javax.inject.Inject

class WeatherForeCastRemoteDataSource @Inject constructor(
    private val weatherForeCastApiService: WeatherForeCastApiService
): NetworkDataSource() {

    suspend fun getWeatherForeCast(latitude: String, longitude: String) = getResult{
        weatherForeCastApiService.getDailyWeatherForeCast(latitude, longitude)
    }


}