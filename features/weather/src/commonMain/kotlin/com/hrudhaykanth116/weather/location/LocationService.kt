package com.hrudhaykanth116.weather.location

interface LocationService {
    suspend fun getCurrentLocation(): LocationResult?
    suspend fun getAddressFromCoordinates(latitude: Double, longitude: Double): String?
}

expect class LocationServiceImpl : LocationService {
    override suspend fun getCurrentLocation(): LocationResult?
    override suspend fun getAddressFromCoordinates(
        latitude: Double,
        longitude: Double,
    ): String?
}
