package com.hrudhaykanth116.composeapp.domain

import com.hrudhaykanth116.composeapp.domain.model.RemoteAppConfig
import com.hrudhaykanth116.composeapp.models.Feature
import kotlinx.coroutines.flow.Flow

class GetRemoteConfigUseCase(private val repository: IRemoteConfigRepository) {

    suspend operator fun invoke(): RemoteAppConfig {
        repository.fetchAndCache()
        return buildConfig()
    }

    fun getCached(): RemoteAppConfig = buildConfig()

    fun configUpdates(): Flow<Unit> = repository.configUpdates()

    private fun buildConfig(): RemoteAppConfig {

        // ORDER MATTERS
        val gateConfig = listOf(
            repository.getForceGate(),
            repository.getMaintenanceGate(),
            repository.getMessageGate()
        ).firstOrNull { it.isEnabled }

        return RemoteAppConfig(
            appGateConfig = gateConfig,
            features = Feature.entries.filter(repository::isFeatureEnabled)
        )

    }
}
