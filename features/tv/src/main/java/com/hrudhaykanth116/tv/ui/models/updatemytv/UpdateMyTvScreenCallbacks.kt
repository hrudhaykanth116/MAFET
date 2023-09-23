package com.hrudhaykanth116.tv.ui.models.updatemytv

import androidx.compose.ui.text.input.TextFieldValue

data class UpdateMyTvScreenCallbacks(
    val onSeasonChanged: (TextFieldValue) -> Unit,
    val onCancelled: () -> Unit,
)
