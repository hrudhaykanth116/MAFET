package com.hrudhaykanth116.composeapp.home.models

import kotlinx.serialization.Serializable

@Serializable
data class FeatureConfig(
    val key: String = "",
    val enabled: Boolean = false,
)
