package com.hrudhaykanth116.tv.ui.models.updatemytv

import androidx.compose.ui.text.input.TextFieldValue

data class UpdateMyTvScreenCallbacks(
    val onSeasonChanged: (TextFieldValue) -> Unit = {},
    val onEpisodeChanged: (TextFieldValue) -> Unit = {},
    val onLastWatchedDateChanged: (Long?) -> Unit = {},
    val onLastWatchedDatePickerCloseRequest: () -> Unit = {},
    val onLastWatchedDatePickerOpenRequest: () -> Unit = {},
    val onCancelled: () -> Unit = {},
    val onSubmit: () -> Unit = {},
)
