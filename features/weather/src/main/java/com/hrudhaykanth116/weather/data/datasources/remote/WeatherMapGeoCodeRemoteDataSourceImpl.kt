package com.hrudhaykanth116.weather.data.datasources.remote

import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.OpenWeatherApiService
import javax.inject.Inject

class WeatherMapGeoCodeRemoteDataSourceImpl @Inject constructor(
    private val openWeatherApiService: OpenWeatherApiService,
) : IGeoCodeRemoteDataSource, NetworkDataSource() {

    override suspend fun getLocationInfo(location: String) = getResult {
        openWeatherApiService.getLocationInfo(location)
    }

}