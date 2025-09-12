package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem

interface IGeoCodeRepository{

    suspend fun getLocationInfo(location: String): RepoResultWrapper<List<GetLocationInfoResponseItem>>

    suspend fun getReverseGeoCoding(latitude: String, longitude: String): RepoResultWrapper<List<OWMReverseGeocodingResponseItem>>

}