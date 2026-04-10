package com.hrudhaykanth116.composeapp.domain.model

import com.hrudhaykanth116.composeapp.models.Feature

data class RemoteAppConfig(
    val appGateConfig: AppGateConfig? = null,
    val features: List<Feature> = emptyList(),
)