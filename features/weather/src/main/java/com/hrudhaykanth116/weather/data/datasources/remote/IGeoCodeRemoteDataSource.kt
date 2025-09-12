package com.hrudhaykanth116.weather.data.datasources.remote

import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem

interface IGeoCodeRemoteDataSource {

    suspend fun getLocationInfo(location: String): ApiResultWrapper<List<GetLocationInfoResponseItem>>

    suspend fun getReverseGeoCoding(latitude: String, longitude: String): ApiResultWrapper<List<OWMReverseGeocodingResponseItem>>

}