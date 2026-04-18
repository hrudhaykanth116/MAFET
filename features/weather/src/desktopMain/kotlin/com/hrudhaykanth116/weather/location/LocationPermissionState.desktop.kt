package com.hrudhaykanth116.weather.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberLocationPermissionState(
    onPermissionResult: (Boolean) -> Unit
): LocationPermissionState {
    // TODO: Desktop what should be the implementation
    return remember {
        object : LocationPermissionState {
            override val hasPermission: Boolean = true
            override val isLocationEnabled: Boolean = true

            override fun requestPermission() {
                onPermissionResult(true)
            }

            override fun openLocationSettings() {
                // No-op on desktop
            }
        }
    }
}
