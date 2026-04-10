package com.hrudhaykanth116.composeapp.models

import com.hrudhaykanth116.composeapp.domain.model.AppGateConfig

data class AppScreenState(
    val features: List<Feature> = emptyList(),
    val activeGate: AppGateConfig? = null,
)
