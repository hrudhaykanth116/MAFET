package com.hrudhaykanth116.weather.data.datasources.remote

import com.hrudhaykanth116.core.network.NetworkDataSource
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import com.hrudhaykanth116.weather.data.datasources.remote.ktor.OpenWeatherApiServiceKtor
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse

class WeatherForeCastRemoteDataSource(
    private val openWeatherApiService: OpenWeatherApiServiceKtor,
) : NetworkDataSource() {

    suspend fun getWeatherForeCast(
        latitude: String,
        longitude: String,
    ): ApiResultWrapper<WeatherForeCastResponse> =
        getResult {
            openWeatherApiService.getDailyWeatherForeCast(latitude, longitude)
        }

}
