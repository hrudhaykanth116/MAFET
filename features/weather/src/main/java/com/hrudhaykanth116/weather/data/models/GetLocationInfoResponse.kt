package com.hrudhaykanth116.weather.data.models


import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @JsonClass(generateAdapter = true)
@Serializable
data class GetLocationInfoResponseItem(
    @Json(name = "country")
    @SerialName("country")
    val country: String? = null,
    @Json(name = "lat")
    @SerialName("lat")
    val lat: Double? = null,
    @Json(name = "local_names")
    @SerialName("local_names")
    val localNames: LocalNames? = null,
    @Json(name = "lon")
    @SerialName("lon")
    val lon: Double? = null,
    @Json(name = "name")
    @SerialName("name")
    val name: String? = null,
    @Json(name = "state")
    @SerialName("state")
    val state: String? = null
) {
    // @JsonClass(generateAdapter = true)
    @Serializable
    data class LocalNames(
        @Json(name = "ar")
        @SerialName("ar")
        val ar: String? = null,
        @Json(name = "cs")
        @SerialName("cs")
        val cs: String? = null,
        @Json(name = "en")
        @SerialName("en")
        val en: String? = null,
        @Json(name = "eo")
        @SerialName("eo")
        val eo: String? = null,
        @Json(name = "fa")
        @SerialName("fa")
        val fa: String? = null,
        @Json(name = "he")
        @SerialName("he")
        val he: String? = null,
        @Json(name = "hi")
        @SerialName("hi")
        val hi: String? = null,
        @Json(name = "ja")
        @SerialName("ja")
        val ja: String? = null,
        @Json(name = "ka")
        @SerialName("ka")
        val ka: String? = null,
        @Json(name = "kn")
        @SerialName("kn")
        val kn: String? = null,
        @Json(name = "ml")
        @SerialName("ml")
        val ml: String? = null,
        @Json(name = "mr")
        @SerialName("mr")
        val mr: String? = null,
        @Json(name = "oc")
        @SerialName("oc")
        val oc: String? = null,
        @Json(name = "pa")
        @SerialName("pa")
        val pa: String? = null,
        @Json(name = "ru")
        @SerialName("ru")
        val ru: String? = null,
        @Json(name = "sr")
        @SerialName("sr")
        val sr: String? = null,
        @Json(name = "ta")
        @SerialName("ta")
        val ta: String? = null,
        @Json(name = "te")
        @SerialName("te")
        val te: String? = null,
        @Json(name = "tr")
        @SerialName("tr")
        val tr: String? = null,
        @Json(name = "ur")
        @SerialName("ur")
        val ur: String? = null,
        @Json(name = "zh")
        @SerialName("zh")
        val zh: String? = null
    )
}