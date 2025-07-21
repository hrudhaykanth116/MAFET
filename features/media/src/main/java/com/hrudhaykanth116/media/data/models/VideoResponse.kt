package com.hrudhaykanth116.media.data.models

data class VideoResponse(
    val id: Int,
    val video_files: List<VideoFile>,
    val video_pictures: List<VideoPicture>
)

data class VideoFile(
    val id: Int,
    val quality: String,
    val file_type: String,
    val width: Int,
    val height: Int,
    val fps: Double?,
    val link: String
)

data class VideoPicture(
    val id: Int,
    val nr: Int,
    val picture: String
)
