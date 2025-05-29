package com.hrudhaykanth116.weather.data.models

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class OWMReverseGeocodingResponseItem(
    val country: String? = null,
    val lat: Double? = null,
    @Json(name = "local_names")
    val localNames: LocalNames? = null,
    val lon: Double? = null,
    val name: String? = null,
) {
    @JsonClass(generateAdapter = true)
    data class LocalNames(
        val af: String? = null,
        val ar: String? = null,
        val ascii: String? = null,
        val az: String? = null,
        val bg: String? = null,
        val ca: String? = null,
        val da: String? = null,
        val de: String? = null,
        val el: String? = null,
        val en: String? = null,
        val eu: String? = null,
        val fa: String? = null,
        @Json(name = "feature_name")
        val featureName: String? = null,
        val fi: String? = null,
        val fr: String? = null,
        val gl: String? = null,
        val he: String? = null,
        val hi: String? = null,
        val hr: String? = null,
        val hu: String? = null,
        val id: String? = null,
        val `it`: String? = null,
        val ja: String? = null,
        val la: String? = null,
        val lt: String? = null,
        val mk: String? = null,
        val nl: String? = null,
        val no: String? = null,
        val pl: String? = null,
        val pt: String? = null,
        val ro: String? = null,
        val ru: String? = null,
        val sk: String? = null,
        val sl: String? = null,
        val sr: String? = null,
        val th: String? = null,
        val tr: String? = null,
        val vi: String? = null,
        val zu: String? = null,
    )
}