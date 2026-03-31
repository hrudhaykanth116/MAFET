package com.hrudhaykanth116.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createDataStore(): DataStore<Preferences>

internal const val DATASTORE_FILE_NAME = "mafet_preferences.preferences_pb"
