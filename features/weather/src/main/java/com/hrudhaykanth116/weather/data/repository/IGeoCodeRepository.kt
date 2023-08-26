package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem

interface IGeoCodeRepository{

    suspend fun getLocationInfo(location: String): DataResult<List<GetLocationInfoResponseItem>>

}