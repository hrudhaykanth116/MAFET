package com.hrudhaykanth116.weather.data.datasources.remote

import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.OpenWeatherApiService
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem
import javax.inject.Inject

class WeatherMapGeoCodeRemoteDataSourceImpl @Inject constructor(
    private val openWeatherApiService: OpenWeatherApiService,
) : IGeoCodeRemoteDataSource, NetworkDataSource() {

    override suspend fun getLocationInfo(location: String) = getResult {
        openWeatherApiService.getLocationInfo(location)
    }

    override suspend fun getReverseGeoCoding(
        latitude: String,
        longitude: String,
    ): ApiResultWrapper<List<OWMReverseGeocodingResponseItem>> = getResult {
        openWeatherApiService.reverseGeoCoding(latitude, longitude)
    }

}