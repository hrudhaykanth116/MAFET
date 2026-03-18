package com.hrudhaykanth116.weather.data.datasources.remote

import com.hrudhaykanth116.core.network.NetworkDataSource
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import com.hrudhaykanth116.weather.data.datasources.remote.ktor.OpenWeatherApiServiceKtor
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem

class WeatherMapGeoCodeRemoteDataSourceImpl(
    private val openWeatherApiService: OpenWeatherApiServiceKtor,
) : IGeoCodeRemoteDataSource, NetworkDataSource() {

    override suspend fun getLocationInfo(location: String): ApiResultWrapper<List<GetLocationInfoResponseItem>> = getResult {
        openWeatherApiService.getLocationInfo(location)
    }

    override suspend fun getReverseGeoCoding(
        latitude: String,
        longitude: String,
    ): ApiResultWrapper<List<OWMReverseGeocodingResponseItem>> = getResult {
        openWeatherApiService.reverseGeoCoding(latitude, longitude)
    }

}