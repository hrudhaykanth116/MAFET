package com.hrudhaykanth116.media.data.repositories

import com.hrudhaykanth116.media.data.models.GetPopularVideosResponse
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.models.VideoResponse
import com.hrudhaykanth116.media.data.network.PexelsRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PexelsRepository @Inject constructor(
    private val remote: PexelsRemoteDataSource,
) {
    suspend fun getCuratedPhotos(page: Int, perPage: Int): List<PhotoResponse> {
        return remote.getCuratedPhotos(page, perPage).photos
    }

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): List<PhotoResponse> {
        return remote.searchPhotos(query, page, perPage).photos
    }

    suspend fun getPhotoById(id: Int): PhotoResponse {
        return remote.getPhotoById(id)
    }

    suspend fun getVideoById(id: Int): VideoResponse {
        return remote.getVideoById(id)
    }

    suspend fun getPopularVideos(perPage: Int): GetPopularVideosResponse {
        return remote.getPopularVideos(perPage)
    }

}
