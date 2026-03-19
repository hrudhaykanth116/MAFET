package com.hrudhaykanth116.tv.data.datasources.remote.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user_post")
data class UserPost(
    @PrimaryKey(autoGenerate = true)
    val dbId: Int,
    var caption: Caption?,
    @SerialName("created_time")
    var createdTime: String?,
    var filter: String?,
    var id: String?,
    var images: Images?,
    var link: String?,
    var type: String?,
    var user: User?,
    @SerialName("user_has_liked")
    var userHasLiked: Boolean?
) {
    @Serializable
    data class Caption(
        @SerialName("created_time")
        var createdTime: String?,
        var from: User?,
        var id: String?,
        var text: String?
    )

    @Serializable
    data class Images(
        @SerialName("low_resolution")
        var lowResolution: ImageDetails?,
        @SerialName("standard_resolution")
        var standardResolution: ImageDetails?,
        var thumbnail: ImageDetails?
    ) {
        @Serializable
        data class ImageDetails(
            var height: Int?,
            var url: String?,
            var width: Int?
        )
    }

    @Serializable
    data class User(
        @SerialName("full_name")
        var fullName: String?,
        var id: String?,
        @SerialName("profile_picture")
        var profilePictureUrl: String?,
        var username: String?
    )
}