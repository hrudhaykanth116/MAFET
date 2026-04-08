package com.hrudhaykanth116.composeapp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppGateConfig(
    @SerialName("is_enabled") val isEnabled: Boolean = false,
    val type: GateType = GateType.FORCE,
    val title: String = "",
    val message: String = "",
)

@Serializable
enum class GateType {
    /** Non-dismissable. CTA opens Play Store. */
    @SerialName("force") FORCE,
    /** Non-dismissable. No action — just a maintenance message. */
    @SerialName("maintenance") MAINTENANCE,
    /** Dismissable info dialog. User can proceed after reading. */
    @SerialName("message") MESSAGE,
}
