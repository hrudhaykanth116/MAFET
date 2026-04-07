package com.hrudhaykanth116.composeapp.models

data class AppGateConfig(
    val isEnabled: Boolean = false,
    val type: GateType = GateType.FORCE,
    val title: String = "",
    val message: String = "",
    val ctaLabel: String = "Update Now",
    val minVersionCode: Long = 0,
)

enum class GateType {
    /** Non-dismissable. CTA opens Play Store. */
    FORCE,
    /** Dismissable. CTA opens Play Store, "Later" closes. */
    SOFT,
    /** Non-dismissable. No action — just a maintenance message. */
    MAINTENANCE,
    /** Dismissable info dialog. User can proceed after reading. */
    MESSAGE,
}
