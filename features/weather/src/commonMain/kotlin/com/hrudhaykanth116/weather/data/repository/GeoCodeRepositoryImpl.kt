package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.weather.data.datasources.remote.IGeoCodeRemoteDataSource
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem
import kotlinx.coroutines.CoroutineDispatcher

class GeoCodeRepositoryImpl(
    private val geoCodeRemoteDataSource: IGeoCodeRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : IGeoCodeRepository, BaseRepository(dispatcher) {

    override suspend fun getLocationInfo(location: String): RepoResultWrapper<List<GetLocationInfoResponseItem>> = getResult {
        geoCodeRemoteDataSource.getLocationInfo(location)
    }

    override suspend fun getReverseGeoCoding(
        latitude: String,
        longitude: String,
    ): RepoResultWrapper<List<OWMReverseGeocodingResponseItem>> = getResult {
        geoCodeRemoteDataSource.getReverseGeoCoding(latitude, longitude)
    }

}
