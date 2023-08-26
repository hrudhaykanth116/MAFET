package com.hrudhaykanth116.weather.data.datasources.remote

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem

interface IGeoCodeRemoteDataSource {

    suspend fun getLocationInfo(location: String): DataResult<List<GetLocationInfoResponseItem>>

}