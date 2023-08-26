package com.hrudhaykanth116.weather.data.models


import com.squareup.moshi.Json

// @JsonClass(generateAdapter = true)
data class GetLocationInfoResponseItem(
    @Json(name = "country")
    val country: String? = null,
    @Json(name = "lat")
    val lat: Double? = null,
    @Json(name = "local_names")
    val localNames: LocalNames? = null,
    @Json(name = "lon")
    val lon: Double? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "state")
    val state: String? = null
) {
    // @JsonClass(generateAdapter = true)
    data class LocalNames(
        @Json(name = "ar")
        val ar: String? = null,
        @Json(name = "cs")
        val cs: String? = null,
        @Json(name = "en")
        val en: String? = null,
        @Json(name = "eo")
        val eo: String? = null,
        @Json(name = "fa")
        val fa: String? = null,
        @Json(name = "he")
        val he: String? = null,
        @Json(name = "hi")
        val hi: String? = null,
        @Json(name = "ja")
        val ja: String? = null,
        @Json(name = "ka")
        val ka: String? = null,
        @Json(name = "kn")
        val kn: String? = null,
        @Json(name = "ml")
        val ml: String? = null,
        @Json(name = "mr")
        val mr: String? = null,
        @Json(name = "oc")
        val oc: String? = null,
        @Json(name = "pa")
        val pa: String? = null,
        @Json(name = "ru")
        val ru: String? = null,
        @Json(name = "sr")
        val sr: String? = null,
        @Json(name = "ta")
        val ta: String? = null,
        @Json(name = "te")
        val te: String? = null,
        @Json(name = "tr")
        val tr: String? = null,
        @Json(name = "ur")
        val ur: String? = null,
        @Json(name = "zh")
        val zh: String? = null
    )
}