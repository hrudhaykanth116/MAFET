package com.hrudhaykanth116.tv.data.datasources.remote.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "user_post")
data class UserPost(
    @PrimaryKey(autoGenerate = true)
    val dbId: Int,
    var caption: Caption?,
    @Json(name = "created_time")
    var createdTime: String?,
    var filter: String?,
    var id: String?,
    var images: Images?,
    var link: String?,
    var type: String?,
    var user: User?,
    @Json(name = "user_has_liked")
    var userHasLiked: Boolean?
) {
    @JsonClass(generateAdapter = true)
    data class Caption(
        @Json(name = "created_time")
        var createdTime: String?,
        var from: User?,
        var id: String?,
        var text: String?
    )

    @JsonClass(generateAdapter = true)
    data class Images(
        @Json(name = "low_resolution")
        var lowResolution: ImageDetails?,
        @Json(name = "standard_resolution")
        var standardResolution: ImageDetails?,
        var thumbnail: ImageDetails?
    ) {
        @JsonClass(generateAdapter = true)
        data class ImageDetails(
            var height: Int?,
            var url: String?,
            var width: Int?
        )
    }

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "full_name")
        var fullName: String?,
        var id: String?,
        @Json(name = "profile_picture")
        var profilePictureUrl: String?,
        var username: String?
    )
}