package com.hrudhaykanth116.composeapp.models

import com.hrudhaykanth116.composeapp.home.models.FeatureConfig

data class RemoteAppConfig(
    val appGate: AppGateConfig,
    val features: List<FeatureConfig>,
)