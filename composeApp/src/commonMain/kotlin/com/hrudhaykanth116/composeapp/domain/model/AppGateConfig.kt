package com.hrudhaykanth116.composeapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppGateConfig(
    @SerialName("is_enabled") val isEnabled: Boolean = false,
    val title: String = "",
    val message: String = "",
    val buttons: List<GateButton> = emptyList(),
)

@Serializable
data class GateButton(
    val text: String = "",
    val action: String = "",
)

/** Well-known action constants for [GateButton.action]. */
object GateAction {
    const val DISMISS = "dismiss"
}
