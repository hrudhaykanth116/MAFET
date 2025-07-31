package com.hrudhaykanth116.core.common.utils.gps

import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient

object GpsUtils {

    fun isGpsEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun requestEnableGps(context: Context, onResolvable: (IntentSenderRequest) -> Unit) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 10000L
        ).build()

        val settingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)
            .build()

        val client: SettingsClient = LocationServices.getSettingsClient(context)

        client.checkLocationSettings(settingsRequest)
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        val intentSenderRequest =
                            IntentSenderRequest.Builder(exception.resolution).build()
                        onResolvable(intentSenderRequest)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        sendEx.printStackTrace()
                    }
                }
            }
    }



}