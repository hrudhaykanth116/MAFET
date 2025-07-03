package com.hrudhaykanth116.media.data.models

data class PhotoResponse(
    val id: Int,
    val url: String,
    val photographer: String,
    val src: PhotoSrc
)

data class PhotoSrc(
    val original: String,
    val medium: String,
    val small: String
)
