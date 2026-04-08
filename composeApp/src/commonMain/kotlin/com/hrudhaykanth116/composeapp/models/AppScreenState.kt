package com.hrudhaykanth116.composeapp.models

import com.hrudhaykanth116.composeapp.home.models.FeatureConfig

data class AppScreenState(
    val features: List<FeatureConfig> = emptyList(),
    val blockingConfig: AppGateConfig = AppGateConfig(),
    val dialogConfig: AppEntryDialogRemoteConfig? = null,
)
