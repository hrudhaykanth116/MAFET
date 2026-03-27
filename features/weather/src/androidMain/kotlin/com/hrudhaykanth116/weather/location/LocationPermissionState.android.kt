package com.hrudhaykanth116.weather.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

@Composable
actual fun rememberLocationPermissionState(
    onPermissionResult: (Boolean) -> Unit
): LocationPermissionState {
    val context = LocalContext.current

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PermissionChecker.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
        onPermissionResult(granted)
    }

    return remember(hasPermission) {
        object : LocationPermissionState {
            override val hasPermission: Boolean
                get() = hasPermission

            override val isLocationEnabled: Boolean
                get() {
                    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
                    return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
                            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
                }

            override fun requestPermission() {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            override fun openLocationSettings() {
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        }
    }
}
