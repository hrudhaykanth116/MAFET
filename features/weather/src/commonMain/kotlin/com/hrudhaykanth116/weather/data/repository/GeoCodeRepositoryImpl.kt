package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.data.mappers.toDomainResult
import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.weather.data.datasources.remote.IGeoCodeRemoteDataSource
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem
import com.hrudhaykanth116.weather.domain.repository.IGeoCodeRepository
import kotlinx.coroutines.CoroutineDispatcher

class GeoCodeRepositoryImpl(
    private val geoCodeRemoteDataSource: IGeoCodeRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : IGeoCodeRepository, BaseRepository(dispatcher) {

    override suspend fun getLocationInfo(location: String): DomainResult<List<GetLocationInfoResponseItem>> {
        val repoResult = getResult {
            geoCodeRemoteDataSource.getLocationInfo(location)
        }
        return repoResult.toDomainResult()
    }

    override suspend fun getReverseGeoCoding(
        latitude: String,
        longitude: String,
    ): DomainResult<List<OWMReverseGeocodingResponseItem>> {
        val repoResult = getResult {
            geoCodeRemoteDataSource.getReverseGeoCoding(latitude, longitude)
        }
        return repoResult.toDomainResult()
    }

}
