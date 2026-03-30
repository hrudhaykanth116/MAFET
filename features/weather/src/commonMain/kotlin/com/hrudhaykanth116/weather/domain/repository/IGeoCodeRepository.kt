package com.hrudhaykanth116.weather.domain.repository

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem

interface IGeoCodeRepository {

    suspend fun getLocationInfo(location: String): DomainResult<List<GetLocationInfoResponseItem>>

    suspend fun getReverseGeoCoding(latitude: String, longitude: String): DomainResult<List<OWMReverseGeocodingResponseItem>>

}
