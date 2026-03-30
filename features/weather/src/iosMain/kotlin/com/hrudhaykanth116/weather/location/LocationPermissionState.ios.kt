package com.hrudhaykanth116.weather.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.darwin.NSObject

@Composable
actual fun rememberLocationPermissionState(
    onPermissionResult: (Boolean) -> Unit
): LocationPermissionState {
    var hasPermission by remember { mutableStateOf(checkLocationPermission()) }
    var isLocationEnabled by remember { mutableStateOf(CLLocationManager.locationServicesEnabled()) }

    val locationManager = remember { CLLocationManager() }

    val delegate = remember {
        object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
                val granted = checkLocationPermission()
                hasPermission = granted
                isLocationEnabled = CLLocationManager.locationServicesEnabled()
                onPermissionResult(granted)
            }

            @Suppress("CONFLICTING_OVERLOADS")
            override fun locationManager(
                manager: CLLocationManager,
                didChangeAuthorizationStatus: CLAuthorizationStatus
            ) {
                val granted = checkLocationPermission()
                hasPermission = granted
                isLocationEnabled = CLLocationManager.locationServicesEnabled()
                onPermissionResult(granted)
            }
        }
    }

    DisposableEffect(locationManager) {
        locationManager.delegate = delegate
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        onDispose {
            locationManager.delegate = null
        }
    }

    return remember(hasPermission, isLocationEnabled) {
        object : LocationPermissionState {
            override val hasPermission: Boolean
                get() = hasPermission

            override val isLocationEnabled: Boolean
                get() = isLocationEnabled

            override fun requestPermission() {
                locationManager.requestWhenInUseAuthorization()
            }

            override fun openLocationSettings() {
                val url = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
                if (url != null) {
                    UIApplication.sharedApplication.openURL(url)
                }
            }
        }
    }
}

private fun checkLocationPermission(): Boolean {
    val status = CLLocationManager.authorizationStatus()
    return status == kCLAuthorizationStatusAuthorizedWhenInUse ||
            status == kCLAuthorizationStatusAuthorizedAlways
}
