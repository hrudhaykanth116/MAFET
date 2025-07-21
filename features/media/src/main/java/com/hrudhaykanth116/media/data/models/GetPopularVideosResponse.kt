package com.hrudhaykanth116.media.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetPopularVideosResponse(
    val page: Int? = null,
    @Json(name = "per_page")
    val perPage: Int? = null,
    val videos: List<Video?>? = null,
    @Json(name = "total_results")
    val totalResults: Int? = null,
    @Json(name = "next_page")
    val nextPage: String? = null,
    val url: String? = null
) {
    @JsonClass(generateAdapter = true)
    data class Video(
        val id: Int? = null,
        val width: Int? = null,
        val height: Int? = null,
        val duration: Int? = null,
        @Json(name = "full_res")
        val fullRes: Any? = null,
        val tags: List<Any?>? = null,
        val url: String? = null,
        val image: String? = null,
        @Json(name = "avg_color")
        val avgColor: Any? = null,
        val user: User? = null,
        @Json(name = "video_files")
        val videoFiles: List<VideoFile?>? = null,
        @Json(name = "video_pictures")
        val videoPictures: List<VideoPicture?>? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class User(
            val id: Int? = null,
            val name: String? = null,
            val url: String? = null
        )

        @JsonClass(generateAdapter = true)
        data class VideoFile(
            val id: Int? = null,
            val quality: String? = null,
            @Json(name = "file_type")
            val fileType: String? = null,
            val width: Int? = null,
            val height: Int? = null,
            val fps: Double? = null,
            val link: String? = null,
            val size: Int? = null
        )

        @JsonClass(generateAdapter = true)
        data class VideoPicture(
            val id: Int? = null,
            val nr: Int? = null,
            val picture: String? = null
        )
    }
}