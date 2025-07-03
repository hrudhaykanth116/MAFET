package com.hrudhaykanth116.media.data.network;

import com.hrudhaykanth116.media.data.models.CuratedPhotosResponse
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.models.PhotoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApisService {

    @GET("v1/search")
    suspend fun searchPhotos(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 15
    ): PhotoSearchResponse

    @GET("v1/curated")
    suspend fun getCuratedPhotos(
        @Header("Authorization") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 15
    ): CuratedPhotosResponse

    @GET("v1/photos/{id}")
    suspend fun getPhotoById(
        @Header("Authorization") apiKey: String,
        @Path("id") id: Int
    ): PhotoResponse
}
