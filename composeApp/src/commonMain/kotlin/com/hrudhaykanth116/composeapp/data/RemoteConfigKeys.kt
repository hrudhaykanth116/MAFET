package com.hrudhaykanth116.composeapp.data

import com.hrudhaykanth116.composeapp.models.Feature

internal object RemoteConfigKeys {
    const val APP_GATE_FORCE       = "app_gate_force"
    const val APP_GATE_MAINTENANCE = "app_gate_maintenance"
    const val APP_GATE_MESSAGE     = "app_gate_message"
    fun featureFlag(feature: Feature): String = "feature_${feature.key}"
}
