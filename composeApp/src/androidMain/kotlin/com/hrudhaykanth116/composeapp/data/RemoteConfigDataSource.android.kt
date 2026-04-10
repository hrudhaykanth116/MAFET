package com.hrudhaykanth116.composeapp.data

import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.hrudhaykanth116.composeapp.models.Feature
import com.hrudhaykanth116.core.common.utils.log.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class RemoteConfigDataSource actual constructor(fetchIntervalSeconds: Long) {

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    private val _configUpdates = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    init {
        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(fetchIntervalSeconds)
            .build()
        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.setDefaultsAsync(buildDefaults())
        setupRealTimeListener()
    }

    actual suspend fun fetchAndActivate(): Boolean =
        suspendCancellableCoroutine { cont ->
            remoteConfig.fetchAndActivate()
                .addOnSuccessListener { cont.resume(true) }
                .addOnFailureListener { e ->
                    Logger.e(TAG, "Remote Config fetch failed, using cached/defaults", e)
                    cont.resume(false)
                }
        }

    actual fun getString(key: String): String = remoteConfig.getString(key)

    actual fun getBoolean(key: String): Boolean = remoteConfig.getBoolean(key)

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

    private fun buildDefaults(): Map<String, Any> = buildMap {
        put(RemoteConfigKeys.APP_GATE_FORCE,       DISABLED_FORCE_GATE_JSON)
        put(RemoteConfigKeys.APP_GATE_MAINTENANCE, DISABLED_MAINTENANCE_GATE_JSON)
        put(RemoteConfigKeys.APP_GATE_MESSAGE,     DISABLED_MESSAGE_GATE_JSON)
        Feature.entries.forEach {
            if(it == Feature.TODO){
                put(RemoteConfigKeys.featureFlag(it), true) // Only enable todo when remote config is not fetched for now.
            }else{
                put(RemoteConfigKeys.featureFlag(it), false)
            }
        }
    }

    companion object {
        private const val TAG = "RemoteConfigDataSource"
        private const val DISABLED_FORCE_GATE_JSON =
            """{"is_enabled":false,"type":"force","title":"","message":"","buttons":[]}"""
        private const val DISABLED_MAINTENANCE_GATE_JSON =
            """{"is_enabled":false,"type":"maintenance","title":"","message":"","buttons":[]}"""
        private const val DISABLED_MESSAGE_GATE_JSON =
            """{"is_enabled":false,"type":"message","title":"","message":"","buttons":[]}"""
    }
}
