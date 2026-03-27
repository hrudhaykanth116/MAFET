package com.hrudhaykanth116.weather.location

import androidx.compose.runtime.Composable

interface LocationPermissionState {
    val hasPermission: Boolean
    val isLocationEnabled: Boolean
    fun requestPermission()
    fun openLocationSettings()
}

@Composable
expect fun rememberLocationPermissionState(
    onPermissionResult: (Boolean) -> Unit
): LocationPermissionState
