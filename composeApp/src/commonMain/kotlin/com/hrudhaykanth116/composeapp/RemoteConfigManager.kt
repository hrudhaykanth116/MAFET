package com.hrudhaykanth116.composeapp

import com.hrudhaykanth116.composeapp.home.models.FeatureConfig
import com.hrudhaykanth116.composeapp.models.RemoteAppConfig
import kotlinx.coroutines.flow.Flow

expect class RemoteConfigManager() {
    suspend fun fetchConfig(): RemoteAppConfig
    fun getCachedFeatures(): List<FeatureConfig>

    /**
     * Emits [Unit] whenever Firebase pushes a config update while the app is running.
     * Subscribe in [AppViewModel] to reload config in real time without any polling.
     */
    fun configUpdates(): Flow<Unit>
}
