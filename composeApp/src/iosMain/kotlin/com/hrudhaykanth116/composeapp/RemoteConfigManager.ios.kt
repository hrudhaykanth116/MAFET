package com.hrudhaykanth116.composeapp

import com.hrudhaykanth116.composeapp.home.models.FeatureConfig
import com.hrudhaykanth116.composeapp.models.AppGateConfig
import com.hrudhaykanth116.composeapp.models.Feature
import com.hrudhaykanth116.composeapp.models.RemoteAppConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

actual class RemoteConfigManager actual constructor() {

    actual suspend fun fetchConfig(): RemoteAppConfig = RemoteAppConfig(
        appGate = AppGateConfig(),
        features = listOf(FeatureConfig(Feature.TODO.key, enabled = true)),
        appEntryDialogRemoteConfig = com.hrudhaykanth116.composeapp.models.AppEntryDialogRemoteConfig(),
    )

    actual fun getCachedFeatures(): List<FeatureConfig> = listOf(FeatureConfig(Feature.TODO.key, enabled = true))
    actual fun configUpdates(): Flow<Unit> = emptyFlow()
}
