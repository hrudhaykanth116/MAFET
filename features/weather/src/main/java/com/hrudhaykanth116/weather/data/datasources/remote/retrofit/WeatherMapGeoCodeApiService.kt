package com.hrudhaykanth116.weather.data.datasources.remote.retrofit

import com.hrudhaykanth116.weather.confidential.ApiKeys
import com.hrudhaykanth116.weather.data.models.GetLocationInfoResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherMapGeoCodeApiService {

    @GET("geo/1.0/direct")
    suspend fun getLocationInfo(
        @Query("q") location: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") token: String = ApiKeys.GEO_CODING_API_KEY,
    ): Response<List<GetLocationInfoResponseItem>>

}