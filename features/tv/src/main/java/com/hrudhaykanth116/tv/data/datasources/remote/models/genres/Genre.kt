package com.hrudhaykanth116.tv.data.datasources.remote.models.genres

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Genre(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null
): Parcelable