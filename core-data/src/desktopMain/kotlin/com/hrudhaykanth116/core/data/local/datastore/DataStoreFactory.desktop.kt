package com.hrudhaykanth116.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

private fun dataStoreFilePath(): String {
    val userHome = System.getProperty("user.home")
    val appDir = File(userHome, ".mafet")
    if (!appDir.exists()) {
        appDir.mkdirs()
    }
    return "${appDir.absolutePath}/$DATASTORE_FILE_NAME"
}

actual fun createDataStore(): DataStore<Preferences> {
    return createDataStoreWithPath(
        producePath = { dataStoreFilePath() }
    )
}

private fun createDataStoreWithPath(producePath: () -> String): DataStore<Preferences> =
    androidx.datastore.preferences.core.PreferenceDataStoreFactory.createWithPath(
        produceFile = { File(producePath()) }
    )
