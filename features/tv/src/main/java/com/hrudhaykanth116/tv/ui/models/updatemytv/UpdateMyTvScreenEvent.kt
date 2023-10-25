package com.hrudhaykanth116.tv.ui.models.updatemytv

import androidx.compose.ui.text.input.TextFieldValue

sealed interface UpdateMyTvScreenEvent{
    data class OnSeasonChanged(val textFieldValue: TextFieldValue): UpdateMyTvScreenEvent
    data class OnEpisodeChanged(val textFieldValue: TextFieldValue): UpdateMyTvScreenEvent
    object OnSubmit: UpdateMyTvScreenEvent
}