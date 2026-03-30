package com.hrudhaykanth116.tv.ui.models.updatemytv

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.tv.data.datasources.local.models.WatchStatus

data class UpdateMyTvScreenCallbacks(
    val onSeasonChanged: (TextFieldValue) -> Unit = {},
    val onEpisodeChanged: (TextFieldValue) -> Unit = {},
    val onLastWatchedDateChanged: (Long?) -> Unit = {},
    val onLastWatchedDatePickerCloseRequest: () -> Unit = {},
    val onLastWatchedDatePickerOpenRequest: () -> Unit = {},
    val onStatusChanged: (WatchStatus) -> Unit = {},
    val onRatingChanged: (Int?) -> Unit = {},
    val onNotesChanged: (TextFieldValue) -> Unit = {},
    val onCancelled: () -> Unit = {},
    val onSubmit: () -> Unit = {},
)
