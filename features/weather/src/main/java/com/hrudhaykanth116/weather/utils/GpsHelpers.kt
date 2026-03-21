package com.hrudhaykanth116.weather.utils

import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.hrudhaykanth116.core.common.utils.log.Logger

private const val TAG = "GpsHelpers"

/**
 * Check if GPS is enabled on the device.
 * Checks both GPS_PROVIDER and NETWORK_PROVIDER.
 *
 * @param context Android Context
 * @return true if GPS is enabled, false otherwise
 */
fun isGpsEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        ?: return false

    return try {
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } catch (e: Exception) {
        Logger.e(TAG, "Error checking GPS status", e)
        false
    }
}

/**
 * Request to enable GPS using Google Play Services Location Settings API.
 * Shows a system dialog to the user asking to enable location services.
 *
 * @param context Android Context
 * @param onIntentSenderRequest Callback with IntentSenderRequest to launch
 */
fun requestEnableGps(
    context: Context,
    onIntentSenderRequest: (IntentSenderRequest) -> Unit
) {
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
        .setMinUpdateIntervalMillis(5000)
        .build()

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    val client = LocationServices.getSettingsClient(context)
    val task = client.checkLocationSettings(builder.build())

    task.addOnSuccessListener {
        Logger.d(TAG, "GPS is already enabled")
    }

    task.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                onIntentSenderRequest(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                Logger.e(TAG, "Error requesting GPS enable", sendEx)
            }
        } else {
            Logger.e(TAG, "GPS enable request failed", exception)
        }
    }
}
