package com.hrudhaykanth116.media.data.network

import com.hrudhaykanth116.media.data.models.CuratedPhotosResponse
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.models.PhotoSearchResponse
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PexelsRemoteDataSource @Inject constructor(
    private val api: PexelsApisService,
    @param:Named("pexels_api_key") private val apiKey: String
) {

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): PhotoSearchResponse =
        api.searchPhotos(apiKey, query, page, perPage)

    suspend fun getCuratedPhotos(page: Int, perPage: Int): CuratedPhotosResponse =
        api.getCuratedPhotos(apiKey, page, perPage)

    suspend fun getPhotoById(id: Int): PhotoResponse =
        api.getPhotoById(apiKey, id)

}
