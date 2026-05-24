package com.hrudhaykanth116.weather.location

// TODO: Desktop what should be the actual implementation
actual class LocationServiceImpl : LocationService {

    actual override suspend fun getCurrentLocation(): LocationResult? {
        // Desktop does not have GPS; return null to trigger fallback/manual entry
        return null
    }

    actual override suspend fun getAddressFromCoordinates(latitude: Double, longitude: Double): String? {
        // No reverse geocoding on desktop
        return null
    }
}
