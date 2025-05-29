package com.hrudhaykanth116.weather.domain.models.location

import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem

data class GeoLocationInfo(
    val oWMReverseGeocodingResponseItem: OWMReverseGeocodingResponseItem
)