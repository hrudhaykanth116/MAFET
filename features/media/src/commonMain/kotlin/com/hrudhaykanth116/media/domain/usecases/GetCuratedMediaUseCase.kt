package com.hrudhaykanth116.media.domain.usecases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.media.data.repositories.PexelsRepository
import com.hrudhaykanth116.media.domain.mappers.MediaItemMapper
import com.hrudhaykanth116.media.domain.models.FilterState
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType

class GetCuratedMediaUseCase(
    private val repository: PexelsRepository
) {

    suspend operator fun invoke(
        mediaType: MediaType,
        page: Int,
        perPage: Int = 20,
        filters: FilterState = FilterState()
    ): DomainResult<List<MediaItem>> {
        return when (mediaType) {
            MediaType.PHOTOS -> {
                when (val result = repository.getCuratedPhotos(
                    page = page,
                    perPage = perPage,
                    orientation = filters.orientation.value,
                    color = filters.color.value,
                    size = filters.size.value
                )) {
                    is DomainResult.Success -> {
                        val mediaItems = result.data.photos.map { photo ->
                            MediaItemMapper.fromPhotoResponse(photo)
                        }
                        DomainResult.Success(mediaItems)
                    }
                    is DomainResult.Error -> DomainResult.Error(result.error)
                }
            }
            MediaType.VIDEOS -> {
                when (val result = repository.getPopularVideos(
                    page = page,
                    perPage = perPage
                )) {
                    is DomainResult.Success -> {
                        val mediaItems = result.data.videos?.filterNotNull()?.map { video ->
                            MediaItemMapper.fromPopularVideoResponse(video)
                        } ?: emptyList()
                        DomainResult.Success(mediaItems)
                    }
                    is DomainResult.Error -> DomainResult.Error(result.error)
                }
            }
        }
    }
}
