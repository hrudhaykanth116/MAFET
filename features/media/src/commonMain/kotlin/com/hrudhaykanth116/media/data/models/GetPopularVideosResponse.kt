package com.hrudhaykanth116.media.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class GetPopularVideosResponse(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("per_page")
    val perPage: Int? = null,
    @SerialName("videos")
    val videos: List<Video?>? = null,
    @SerialName("total_results")
    val totalResults: Int? = null,
    @SerialName("next_page")
    val nextPage: String? = null,
    @SerialName("url")
    val url: String? = null
) {
    @Serializable
    data class Video(
        @SerialName("id")
        val id: Int? = null,
        @SerialName("width")
        val width: Int? = null,
        @SerialName("height")
        val height: Int? = null,
        @SerialName("duration")
        val duration: Int? = null,
        @SerialName("full_res")
        val fullRes: JsonElement? = null,
        @SerialName("tags")
        val tags: List<JsonElement?>? = null,
        @SerialName("url")
        val url: String? = null,
        @SerialName("image")
        val image: String? = null,
        @SerialName("avg_color")
        val avgColor: JsonElement? = null,
        @SerialName("user")
        val user: User? = null,
        @SerialName("video_files")
        val videoFiles: List<VideoFile?>? = null,
        @SerialName("video_pictures")
        val videoPictures: List<VideoPicture?>? = null
    ) {
        @Serializable
        data class User(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null,
            @SerialName("url")
            val url: String? = null
        )

        @Serializable
        data class VideoFile(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("quality")
            val quality: String? = null,
            @SerialName("file_type")
            val fileType: String? = null,
            @SerialName("width")
            val width: Int? = null,
            @SerialName("height")
            val height: Int? = null,
            @SerialName("fps")
            val fps: Double? = null,
            @SerialName("link")
            val link: String? = null,
            @SerialName("size")
            val size: Int? = null
        )

        @Serializable
        data class VideoPicture(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("nr")
            val nr: Int? = null,
            @SerialName("picture")
            val picture: String? = null
        )
    }
}