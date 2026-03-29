package com.hrudhaykanth116.media.data.network

import com.hrudhaykanth116.core.network.NetworkDataSource
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import com.hrudhaykanth116.media.data.models.CuratedPhotosResponse
import com.hrudhaykanth116.media.data.models.GetPopularVideosResponse
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.models.PhotoSearchResponse
import com.hrudhaykanth116.media.data.models.VideoResponse
import com.hrudhaykanth116.media.data.network.ktor.PexelsApiServiceKtor

class PexelsRemoteDataSource(
    private val api: PexelsApiServiceKtor,
    private val apiKey: String
) : NetworkDataSource() {

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): ApiResultWrapper<PhotoSearchResponse> =
        getResult {
            api.searchPhotos(query, page, perPage, apiKey)
        }

    suspend fun getCuratedPhotos(page: Int, perPage: Int): ApiResultWrapper<CuratedPhotosResponse> =
        getResult {
            api.getCuratedPhotos(page, perPage, apiKey)
        }

    suspend fun getPhotoById(id: Int): ApiResultWrapper<PhotoResponse> =
        getResult {
            api.getPhotoById(id, apiKey)
        }

    suspend fun getVideoById(id: Int): ApiResultWrapper<VideoResponse> =
        getResult {
            api.getVideoById(id, apiKey)
        }

    suspend fun getPopularVideos(perPage: Int): ApiResultWrapper<GetPopularVideosResponse> =
        getResult {
            api.getPopularVideos(perPage, apiKey)
        }

}
