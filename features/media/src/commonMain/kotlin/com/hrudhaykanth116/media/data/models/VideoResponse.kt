package com.hrudhaykanth116.media.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("url")
    val url: String,
    @SerialName("user")
    val user: VideoUser,
    @SerialName("video_files")
    val video_files: List<VideoFile>,
    @SerialName("video_pictures")
    val video_pictures: List<VideoPicture>
)

@Serializable
data class VideoUser(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)

@Serializable
data class VideoFile(
    @SerialName("id")
    val id: Int,
    @SerialName("quality")
    val quality: String,
    @SerialName("file_type")
    val file_type: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("fps")
    val fps: Double? = null,
    @SerialName("link")
    val link: String
)

@Serializable
data class VideoPicture(
    @SerialName("id")
    val id: Int,
    @SerialName("nr")
    val nr: Int,
    @SerialName("picture")
    val picture: String
)
