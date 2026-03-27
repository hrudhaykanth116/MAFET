package com.hrudhaykanth116.weather.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetLocationInfoResponseItem(
    @SerialName("country")
    val country: String? = null,
    @SerialName("lat")
    val lat: Double? = null,
    @SerialName("local_names")
    val localNames: LocalNames? = null,
    @SerialName("lon")
    val lon: Double? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("state")
    val state: String? = null
) {
    @Serializable
    data class LocalNames(
        @SerialName("ar")
        val ar: String? = null,
        @SerialName("cs")
        val cs: String? = null,
        @SerialName("en")
        val en: String? = null,
        @SerialName("eo")
        val eo: String? = null,
        @SerialName("fa")
        val fa: String? = null,
        @SerialName("he")
        val he: String? = null,
        @SerialName("hi")
        val hi: String? = null,
        @SerialName("ja")
        val ja: String? = null,
        @SerialName("ka")
        val ka: String? = null,
        @SerialName("kn")
        val kn: String? = null,
        @SerialName("ml")
        val ml: String? = null,
        @SerialName("mr")
        val mr: String? = null,
        @SerialName("oc")
        val oc: String? = null,
        @SerialName("pa")
        val pa: String? = null,
        @SerialName("ru")
        val ru: String? = null,
        @SerialName("sr")
        val sr: String? = null,
        @SerialName("ta")
        val ta: String? = null,
        @SerialName("te")
        val te: String? = null,
        @SerialName("tr")
        val tr: String? = null,
        @SerialName("ur")
        val ur: String? = null,
        @SerialName("zh")
        val zh: String? = null
    )
}
