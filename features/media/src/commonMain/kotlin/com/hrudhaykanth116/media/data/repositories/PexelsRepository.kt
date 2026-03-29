package com.hrudhaykanth116.media.data.repositories

import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.media.data.models.CuratedPhotosResponse
import com.hrudhaykanth116.media.data.models.GetPopularVideosResponse
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.models.PhotoSearchResponse
import com.hrudhaykanth116.media.data.models.VideoResponse
import com.hrudhaykanth116.media.data.network.PexelsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher

class PexelsRepository(
    private val remote: PexelsRemoteDataSource,
    dispatcher: CoroutineDispatcher,
) : BaseRepository(dispatcher) {

    suspend fun getCuratedPhotos(page: Int, perPage: Int): RepoResultWrapper<CuratedPhotosResponse> =
        getResult {
            remote.getCuratedPhotos(page, perPage)
        }

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): RepoResultWrapper<PhotoSearchResponse> =
        getResult {
            remote.searchPhotos(query, page, perPage)
        }

    suspend fun getPhotoById(id: Int): RepoResultWrapper<PhotoResponse> =
        getResult {
            remote.getPhotoById(id)
        }

    suspend fun getVideoById(id: Int): RepoResultWrapper<VideoResponse> =
        getResult {
            remote.getVideoById(id)
        }

    suspend fun getPopularVideos(perPage: Int): RepoResultWrapper<GetPopularVideosResponse> =
        getResult {
            remote.getPopularVideos(perPage)
        }

}
