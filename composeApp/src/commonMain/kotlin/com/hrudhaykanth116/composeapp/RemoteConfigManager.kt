package com.hrudhaykanth116.composeapp

import com.hrudhaykanth116.composeapp.home.models.FeatureConfig
import com.hrudhaykanth116.composeapp.models.RemoteAppConfig

expect class RemoteConfigManager() {
    suspend fun fetchConfig(): RemoteAppConfig
    fun getCachedFeatures(): List<FeatureConfig>
}
