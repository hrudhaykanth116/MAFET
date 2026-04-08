package com.hrudhaykanth116.composeapp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppEntryDialogRemoteConfig(
    @SerialName("is_enabled") val isEnabled: Boolean = false,
    val title: String = "",
    val description: String = "",
    @SerialName("is_dismissable") val isDismissable: Boolean = true,
    val buttons: List<DialogButtonConfig> = emptyList(),
)

@Serializable
data class DialogButtonConfig(
    val text: String = "",
    val action: String = "",
)
