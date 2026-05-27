package com.hrudhaykanth116.core.data.local.datastore

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val LAST_LOCATION_LATITUDE = doublePreferencesKey("last_location_latitude")
    val LAST_LOCATION_LONGITUDE = doublePreferencesKey("last_location_longitude")
    val LAST_LOCATION_ADDRESS = stringPreferencesKey("last_location_address")
    val LAST_LOCATION_TIMESTAMP = longPreferencesKey("last_location_timestamp")
    
    val APP_THEME = stringPreferencesKey("app_theme")
}
