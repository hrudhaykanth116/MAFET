package com.hrudhaykanth116.composeapp

import com.hrudhaykanth116.composeapp.home.models.FeatureConfig
import com.hrudhaykanth116.composeapp.models.AppGateConfig
import com.hrudhaykanth116.composeapp.models.Feature
import com.hrudhaykanth116.composeapp.models.RemoteAppConfig

actual class RemoteConfigManager actual constructor() {

    actual suspend fun fetchConfig(): RemoteAppConfig = RemoteAppConfig(
        appGate = AppGateConfig(),
        features = listOf(FeatureConfig(Feature.TODO.key, enabled = true)),
    )

    actual fun getCachedFeatures(): List<FeatureConfig> =  listOf(FeatureConfig(Feature.TODO.key, enabled = true))
}
