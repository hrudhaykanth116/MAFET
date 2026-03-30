package com.hrudhaykanth116.media.data.repositories

import com.hrudhaykanth116.core.domain.result.DomainResult
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

    suspend fun getCuratedPhotos(page: Int, perPage: Int): DomainResult<CuratedPhotosResponse> =
        fetchResult {
            remote.getCuratedPhotos(page, perPage)
        }

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): DomainResult<PhotoSearchResponse> =
        fetchResult {
            remote.searchPhotos(query, page, perPage)
        }

    suspend fun getPhotoById(id: Int): DomainResult<PhotoResponse> =
        fetchResult {
            remote.getPhotoById(id)
        }

    suspend fun getVideoById(id: Int): DomainResult<VideoResponse> =
        fetchResult {
            remote.getVideoById(id)
        }

    suspend fun getPopularVideos(perPage: Int): DomainResult<GetPopularVideosResponse> =
        fetchResult {
            remote.getPopularVideos(perPage)
        }

}
