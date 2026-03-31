package com.hrudhaykanth116.media.domain.usecases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.media.data.repositories.PexelsRepository
import com.hrudhaykanth116.media.domain.mappers.MediaItemMapper
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType

class GetMediaDetailUseCase(
    private val repository: PexelsRepository
) {

    suspend operator fun invoke(
        id: Int,
        mediaType: MediaType
    ): DomainResult<MediaItem> {
        return when (mediaType) {
            MediaType.PHOTOS -> {
                when (val result = repository.getPhotoById(id)) {
                    is DomainResult.Success -> {
                        val mediaItem = MediaItemMapper.fromPhotoResponse(result.data)
                        DomainResult.Success(mediaItem)
                    }
                    is DomainResult.Error -> DomainResult.Error(result.error)
                }
            }
            MediaType.VIDEOS -> {
                when (val result = repository.getVideoById(id)) {
                    is DomainResult.Success -> {
                        val mediaItem = MediaItemMapper.fromVideoResponse(result.data)
                        DomainResult.Success(mediaItem)
                    }
                    is DomainResult.Error -> DomainResult.Error(result.error)
                }
            }
        }
    }
}
