package com.hrudhaykanth116.composeapp.data

import com.hrudhaykanth116.composeapp.domain.IRemoteConfigRepository
import com.hrudhaykanth116.composeapp.domain.model.AppGateConfig
import com.hrudhaykanth116.composeapp.models.Feature
import com.hrudhaykanth116.core.common.utils.log.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class RemoteConfigRepository(
    private val dataSource: RemoteConfigDataSource,
) : IRemoteConfigRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    override suspend fun fetchAndCache() {
        val fetched = dataSource.fetchAndActivate()
        Logger.d(TAG, "fetchAndCache: fetched=$fetched")
    }

    override fun getForceGate(): AppGateConfig = parseGate(RemoteConfigKeys.APP_GATE_FORCE)

    override fun getMaintenanceGate(): AppGateConfig = parseGate(RemoteConfigKeys.APP_GATE_MAINTENANCE)

    override fun getMessageGate(): AppGateConfig = parseGate(RemoteConfigKeys.APP_GATE_MESSAGE)

    private fun parseGate(key: String): AppGateConfig {
        val raw = dataSource.getString(key)
        return try {
            if (raw.isBlank()) AppGateConfig()
            else json.decodeFromString<AppGateConfig>(raw)
        } catch (e: Exception) {
            Logger.e(TAG, "Failed to parse gate JSON for key=$key: $raw", e)
            AppGateConfig()
        }
    }

    override fun isFeatureEnabled(feature: Feature): Boolean =
        dataSource.getBoolean(RemoteConfigKeys.featureFlag(feature))

    override fun configUpdates(): Flow<Unit> = dataSource.configUpdates()

    companion object {
        private const val TAG = "RemoteConfigRepository"
    }
}
