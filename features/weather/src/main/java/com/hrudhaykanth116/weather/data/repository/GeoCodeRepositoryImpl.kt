package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.data.BaseRepository
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.weather.data.datasources.remote.IGeoCodeRemoteDataSource
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GeoCodeRepositoryImpl @Inject constructor(
    private val geoCodeRemoteDataSource: IGeoCodeRemoteDataSource
): IGeoCodeRepository, BaseRepository() {

    private val dispatcher = Dispatchers.IO

    override suspend fun getLocationInfo(location: String): RepoResultWrapper<List<GetLocationInfoResponseItem>> = getResult {
        geoCodeRemoteDataSource.getLocationInfo(location)
    }

    override suspend fun getReverseGeoCoding(
        latitude: String,
        longitude: String,
    ): RepoResultWrapper<List<OWMReverseGeocodingResponseItem>> = getResult{
        geoCodeRemoteDataSource.getReverseGeoCoding(latitude, longitude)
    }


}