package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.weather.data.datasources.remote.IGeoCodeRemoteDataSource
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeoCodeRepositoryImpl @Inject constructor(
    private val geoCodeRemoteDataSource: IGeoCodeRemoteDataSource
): IGeoCodeRepository {

    private val dispatcher = Dispatchers.IO

    override suspend fun getLocationInfo(location: String): DataResult<List<GetLocationInfoResponseItem>> = withContext(dispatcher) {
        return@withContext geoCodeRemoteDataSource.getLocationInfo(location)
    }

    override suspend fun getReverseGeoCoding(
        latitude: String,
        longitude: String,
    ): DataResult<List<OWMReverseGeocodingResponseItem>> = withContext(dispatcher){
        return@withContext geoCodeRemoteDataSource.getReverseGeoCoding(latitude, longitude)
    }


}