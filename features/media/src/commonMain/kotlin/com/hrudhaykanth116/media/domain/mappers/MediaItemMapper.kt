package com.hrudhaykanth116.media.domain.mappers

import com.hrudhaykanth116.media.data.models.GetPopularVideosResponse
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.models.VideoResponse
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType
import com.hrudhaykanth116.media.domain.models.OrientationType

object MediaItemMapper {

    fun fromPhotoResponse(photo: PhotoResponse): MediaItem {
        return MediaItem(
            id = photo.id,
            type = MediaType.PHOTOS,
            thumbnailUrl = photo.src.medium,
            originalUrl = photo.src.original,
            mediumUrl = photo.src.medium,
            smallUrl = photo.src.small,
            photographer = photo.photographer,
            photographerUrl = photo.photographerUrl,
            width = photo.width,
            height = photo.height,
            avgColor = photo.avgColor,
            orientation = determineOrientation(photo.width, photo.height)
        )
    }

    fun fromVideoResponse(video: VideoResponse): MediaItem {
        val thumbnailUrl = video.video_pictures.firstOrNull()?.picture ?: ""
        val hdFile = video.video_files.firstOrNull { it.quality == "hd" }
        val sdFile = video.video_files.firstOrNull { it.quality == "sd" }
        val originalFile = hdFile ?: video.video_files.firstOrNull() ?: sdFile

        return MediaItem(
            id = video.id,
            type = MediaType.VIDEOS,
            thumbnailUrl = thumbnailUrl,
            originalUrl = originalFile?.link ?: "",
            mediumUrl = sdFile?.link ?: originalFile?.link ?: "",
            smallUrl = video.video_files.lastOrNull()?.link ?: "",
            photographer = video.user.name,
            photographerUrl = video.user.url,
            width = video.width,
            height = video.height,
            avgColor = null,
            orientation = determineOrientation(video.width, video.height)
        )
    }

    fun fromPopularVideoResponse(video: GetPopularVideosResponse.Video): MediaItem {
        val thumbnailUrl = video.videoPictures?.firstOrNull()?.picture ?: video.image ?: ""
        val hdFile = video.videoFiles?.firstOrNull { it?.quality == "hd" }
        val sdFile = video.videoFiles?.firstOrNull { it?.quality == "sd" }
        val originalFile = hdFile ?: video.videoFiles?.firstOrNull() ?: sdFile

        return MediaItem(
            id = video.id ?: 0,
            type = MediaType.VIDEOS,
            thumbnailUrl = thumbnailUrl,
            originalUrl = originalFile?.link ?: "",
            mediumUrl = sdFile?.link ?: originalFile?.link ?: "",
            smallUrl = video.videoFiles?.lastOrNull()?.link ?: "",
            photographer = video.user?.name ?: "",
            photographerUrl = video.user?.url ?: "",
            width = video.width ?: 0,
            height = video.height ?: 0,
            avgColor = null,
            orientation = determineOrientation(video.width ?: 0, video.height ?: 0)
        )
    }

    private fun determineOrientation(width: Int, height: Int): OrientationType {
        val ratio = width.toFloat() / height.toFloat()
        return when {
            ratio > 1.2f -> OrientationType.LANDSCAPE
            ratio < 0.8f -> OrientationType.PORTRAIT
            else -> OrientationType.SQUARE
        }
    }
}
