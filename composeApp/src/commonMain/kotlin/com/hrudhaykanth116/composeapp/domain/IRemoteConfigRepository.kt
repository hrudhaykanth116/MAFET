package com.hrudhaykanth116.composeapp.domain

import com.hrudhaykanth116.composeapp.domain.model.AppGateConfig
import com.hrudhaykanth116.composeapp.models.Feature
import kotlinx.coroutines.flow.Flow

interface IRemoteConfigRepository {
    suspend fun fetchAndCache()
    fun getForceGate(): AppGateConfig
    fun getMaintenanceGate(): AppGateConfig
    fun getMessageGate(): AppGateConfig
    fun isFeatureEnabled(feature: Feature): Boolean
    fun configUpdates(): Flow<Unit>
}
