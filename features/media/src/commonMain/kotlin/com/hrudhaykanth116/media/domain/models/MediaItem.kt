package com.hrudhaykanth116.media.domain.models

data class MediaItem(
    val id: Int,
    val type: MediaType,
    val thumbnailUrl: String,
    val originalUrl: String,
    val mediumUrl: String,
    val smallUrl: String,
    val photographer: String,
    val photographerUrl: String,
    val width: Int,
    val height: Int,
    val avgColor: String?,
    val orientation: OrientationType
)
