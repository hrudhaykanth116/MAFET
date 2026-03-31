package com.hrudhaykanth116.media.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("url")
    val url: String,
    @SerialName("photographer")
    val photographer: String,
    @SerialName("photographer_url")
    val photographerUrl: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("avg_color")
    val avgColor: String? = null,
    @SerialName("src")
    val src: PhotoSrc
)

@Serializable
data class PhotoSrc(
    @SerialName("original")
    val original: String,
    @SerialName("medium")
    val medium: String,
    @SerialName("small")
    val small: String
)
