package com.hrudhaykanth116.composeapp.data

import com.hrudhaykanth116.composeapp.models.Feature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

actual class RemoteConfigDataSource actual constructor(fetchIntervalSeconds: Long) {

    actual suspend fun fetchAndActivate(): Boolean = true

    actual fun getString(key: String): String = when (key) {
        RemoteConfigKeys.APP_GATE_FORCE,
        RemoteConfigKeys.APP_GATE_MAINTENANCE,
        RemoteConfigKeys.APP_GATE_MESSAGE -> DISABLED_GATE_JSON
        else -> ""
    }

    actual fun getBoolean(key: String): Boolean = key in ENABLED_FEATURES

    actual fun configUpdates(): Flow<Unit> = emptyFlow()

    companion object {
        private val ENABLED_FEATURES = setOf(
            RemoteConfigKeys.featureFlag(Feature.TODO),
            RemoteConfigKeys.featureFlag(Feature.WEATHER),
            RemoteConfigKeys.featureFlag(Feature.WATCHLIST),
            RemoteConfigKeys.featureFlag(Feature.JOURNAL),
            // RemoteConfigKeys.featureFlag(Feature.MEDIA),
        )
        private const val DISABLED_GATE_JSON =
            """{"is_enabled":false,"type":"force","title":"","message":"","buttons":[]}"""
    }
}
