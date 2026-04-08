package com.hrudhaykanth116.composeapp.models

import com.hrudhaykanth116.composeapp.home.models.FeatureConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteAppConfig(
    @SerialName("app_gate") val appGate: AppGateConfig = AppGateConfig(),
    val features: List<FeatureConfig> = emptyList(),
    @SerialName("dynamic_dialog") val dynamicDialog: AppEntryDialogRemoteConfig = AppEntryDialogRemoteConfig(),
)