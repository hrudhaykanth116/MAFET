package com.hrudhaykanth116.weather.data.datasources.remote

import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.IGeoCodeRemoteDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.WeatherMapGeoCodeApiService
import javax.inject.Inject

class WeatherMapGeoCodeRemoteDataSourceImpl @Inject constructor(
    private val weatherMapGeoCodeApiService: WeatherMapGeoCodeApiService
): IGeoCodeRemoteDataSource, NetworkDataSource() {

    override suspend fun getLocationInfo(location: String) = getResult {
        weatherMapGeoCodeApiService.getLocationInfo(location)
    }

}