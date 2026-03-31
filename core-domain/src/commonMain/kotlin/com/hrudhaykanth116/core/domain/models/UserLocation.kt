package com.hrudhaykanth116.core.domain.models

data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val address: String?,
    val timestamp: Long
)
