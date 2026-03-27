package com.hrudhaykanth116.weather.location

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.CLPlacemark
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume

actual class LocationServiceImpl : LocationService {

    private val locationManager = CLLocationManager().apply {
        desiredAccuracy = kCLLocationAccuracyBest
    }

    private val geocoder = CLGeocoder()

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getCurrentLocation(): LocationResult? {
        return suspendCancellableCoroutine { cont ->
            val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    manager.stopUpdatingLocation()
                    manager.delegate = null

                    val location = didUpdateLocations.lastOrNull() as? CLLocation
                    if (location != null) {
                        val coords = location.coordinate.useContents {
                            LocationResult(
                                latitude = latitude,
                                longitude = longitude,
                                addressName = null
                            )
                        }
                        cont.resume(coords)
                    } else {
                        cont.resume(null)
                    }
                }

                override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                    manager.stopUpdatingLocation()
                    manager.delegate = null
                    cont.resume(null)
                }
            }

            locationManager.delegate = delegate
            locationManager.startUpdatingLocation()

            cont.invokeOnCancellation {
                locationManager.stopUpdatingLocation()
                locationManager.delegate = null
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getAddressFromCoordinates(latitude: Double, longitude: Double): String? {
        return suspendCancellableCoroutine { cont ->
            val location = CLLocation(latitude = latitude, longitude = longitude)

            geocoder.reverseGeocodeLocation(location) { placemarks, error ->
                if (error != null || placemarks == null) {
                    cont.resume(null)
                    return@reverseGeocodeLocation
                }

                val placemark = placemarks.firstOrNull() as? CLPlacemark
                val city = placemark?.locality
                cont.resume(city)
            }
        }
    }
}
