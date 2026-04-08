package com.hrudhaykanth116.composeapp

import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.hrudhaykanth116.composeapp.home.models.FeatureConfig
import com.hrudhaykanth116.composeapp.models.RemoteAppConfig
import com.hrudhaykanth116.core.common.utils.log.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import kotlin.coroutines.resume

actual class RemoteConfigManager actual constructor() {

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    private val _configUpdates = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    private val appJson = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    init {
        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(FETCH_INTERVAL_SECONDS)
            .build()
        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.setDefaultsAsync(mapOf(KEY_APP_CONFIG to DEFAULT_CONFIG_JSON))
        setupRealTimeListener()
    }

    actual suspend fun fetchConfig(): RemoteAppConfig {
        val fetched = suspendCancellableCoroutine { cont ->
            remoteConfig.fetchAndActivate()
                .addOnSuccessListener { cont.resume(true) }
                .addOnFailureListener { e ->
                    Logger.e(TAG, "Remote Config fetch failed, using cached/defaults", e)
                    cont.resume(false)
                }
        }
        Logger.d(TAG, "Remote Config fetched=$fetched")
        return parseConfig()
    }

    actual fun getCachedFeatures(): List<FeatureConfig> = parseConfig().features

    actual fun configUpdates(): Flow<Unit> = _configUpdates.asSharedFlow()

    private fun setupRealTimeListener() {
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                remoteConfig.activate().addOnCompleteListener {
                    Logger.d(TAG, "Real-time config activated: ${configUpdate.updatedKeys}")
                    _configUpdates.tryEmit(Unit)
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Logger.e(TAG, "Real-time config update error", error)
            }
        })
    }

    private fun parseConfig(): RemoteAppConfig {
        val raw = remoteConfig.getString(KEY_APP_CONFIG)
        return try {
            appJson.decodeFromString<RemoteAppConfig>(raw)
        } catch (e: Exception) {
            Logger.e(TAG, "Failed to parse app config JSON: $raw", e)
            RemoteAppConfig()
        }
    }

    companion object {
        private const val TAG = "RemoteConfigManager"
        private const val FETCH_INTERVAL_SECONDS = 3600L

        const val KEY_APP_CONFIG = "app_config"

        private val DEFAULT_CONFIG_JSON = """
            {
              "app_gate": {
                "is_enabled": false,
                "type": "force",
                "title": "",
                "message": ""
              },
              "features": [
                {"key": "TODO",          "enabled": true},
                {"key": "WEATHER",       "enabled": true},
                {"key": "DASHBOARD",     "enabled": true},
                {"key": "JOURNAL",       "enabled": true},
                {"key": "ENTERTAINMENT", "enabled": true},
                {"key": "AI",            "enabled": true},
                {"key": "MEDIA",         "enabled": true}
              ],
              "dynamic_dialog": {
                "is_enabled": false,
                "title": "",
                "description": "",
                "is_dismissable": true,
                "buttons": []
              }
            }
        """.trimIndent()
    }
}
