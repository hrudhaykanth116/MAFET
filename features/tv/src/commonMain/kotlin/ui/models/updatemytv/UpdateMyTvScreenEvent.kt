package com.hrudhaykanth116.tv.ui.models.updatemytv

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.tv.data.datasources.local.models.WatchStatus

sealed interface UpdateMyTvScreenEvent {
    data class OnSeasonChanged(val textFieldValue: TextFieldValue) : UpdateMyTvScreenEvent
    data class OnEpisodeChanged(val textFieldValue: TextFieldValue) : UpdateMyTvScreenEvent
    data class OnLastWatchedDateChanged(val time: Long?) : UpdateMyTvScreenEvent
    data class OnStatusChanged(val status: WatchStatus) : UpdateMyTvScreenEvent
    data class OnRatingChanged(val rating: Int?) : UpdateMyTvScreenEvent
    data class OnNotesChanged(val notes: TextFieldValue) : UpdateMyTvScreenEvent
    data object OnLastWatchedDatePickerCloseRequest : UpdateMyTvScreenEvent
    data object OnLastWatchedDatePickerOpenRequest : UpdateMyTvScreenEvent
    data object OnSubmit : UpdateMyTvScreenEvent
}