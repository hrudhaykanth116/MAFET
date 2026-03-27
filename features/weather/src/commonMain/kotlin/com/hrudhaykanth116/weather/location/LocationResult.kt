package com.hrudhaykanth116.weather.location

data class LocationResult(
    val latitude: Double,
    val longitude: Double,
    val addressName: String? = null
)
