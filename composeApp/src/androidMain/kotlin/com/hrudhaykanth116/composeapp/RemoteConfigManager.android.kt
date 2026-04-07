package com.hrudhaykanth116.composeapp

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.hrudhaykanth116.composeapp.home.models.FeatureConfig
import com.hrudhaykanth116.composeapp.models.AppGateConfig
import com.hrudhaykanth116.composeapp.models.GateType
import com.hrudhaykanth116.composeapp.models.RemoteAppConfig
import com.hrudhaykanth116.core.common.utils.log.Logger
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.coroutines.resume

actual class RemoteConfigManager actual constructor() {

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(FETCH_INTERVAL_SECONDS)
            .build()
        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.setDefaultsAsync(defaultValues)
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
        return RemoteAppConfig(
            appGate = parseAppGateConfig(),
            features = parseFeatures(),
        )
    }

    actual fun getCachedFeatures(): List<FeatureConfig> = parseFeatures()

    private fun parseAppGateConfig(): AppGateConfig {
        return AppGateConfig(
            isEnabled = remoteConfig.getBoolean(KEY_GATE_ENABLED),
            type = remoteConfig.getString(KEY_GATE_TYPE).toGateType(),
            title = remoteConfig.getString(KEY_GATE_TITLE),
            message = remoteConfig.getString(KEY_GATE_MESSAGE),
            ctaLabel = remoteConfig.getString(KEY_GATE_CTA_LABEL),
            minVersionCode = remoteConfig.getLong(KEY_GATE_MIN_VERSION_CODE),
        )
    }

    private fun parseFeatures(): List<FeatureConfig> {
        val raw = remoteConfig.getString(KEY_FEATURES)
        return try {
            Json.parseToJsonElement(raw).jsonObject.entries.map { (key, value) ->
                FeatureConfig(
                    key = key.uppercase(),
                    enabled = value.jsonObject["enabled"]?.jsonPrimitive?.boolean ?: true,
                )
            }
        } catch (e: Exception) {
            Logger.e(TAG, "Failed to parse features JSON: $raw", e)
            ALL_FEATURES_DEFAULT
        }
    }

    private fun String.toGateType(): GateType = when (this.lowercase()) {
        "soft" -> GateType.SOFT
        "maintenance" -> GateType.MAINTENANCE
        "message" -> GateType.MESSAGE
        else -> GateType.FORCE
    }

    companion object {
        private const val TAG = "RemoteConfigManager"
        private const val FETCH_INTERVAL_SECONDS = 3600L

        const val KEY_GATE_ENABLED = "app_gate_enabled"
        const val KEY_GATE_TYPE = "app_gate_type"
        const val KEY_GATE_TITLE = "app_gate_title"
        const val KEY_GATE_MESSAGE = "app_gate_message"
        const val KEY_GATE_CTA_LABEL = "app_gate_cta_label"
        const val KEY_GATE_MIN_VERSION_CODE = "app_gate_min_version_code"
        const val KEY_FEATURES = "features"

        private val ALL_FEATURES_DEFAULT = listOf(
            "TODO", "WEATHER", "DASHBOARD", "JOURNAL", "ENTERTAINMENT", "AI", "MEDIA"
        ).map { FeatureConfig(key = it, enabled = true) }

        private val defaultValues = mapOf(
            KEY_GATE_ENABLED to false,
            KEY_GATE_TYPE to "force",
            KEY_GATE_TITLE to "",
            KEY_GATE_MESSAGE to "",
            KEY_GATE_CTA_LABEL to "Update Now",
            KEY_GATE_MIN_VERSION_CODE to 0L,
            KEY_FEATURES to """
                {
                  "TODO":          { "enabled": true },
                  "WEATHER":       { "enabled": true },
                  "DASHBOARD":     { "enabled": true },
                  "JOURNAL":       { "enabled": true },
                  "ENTERTAINMENT": { "enabled": true },
                  "AI":            { "enabled": true },
                  "MEDIA":         { "enabled": true }
                }
            """.trimIndent(),
        )
    }
}
