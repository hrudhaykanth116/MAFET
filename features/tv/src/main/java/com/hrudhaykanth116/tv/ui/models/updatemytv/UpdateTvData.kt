package com.hrudhaykanth116.tv.ui.models.updatemytv

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

data class UpdateMyTvUIStateActual(
    val updateTvData: UpdateTvData? = null,
    // TODO: More suitable way
    val isLoading: Boolean = false,
    val isLastWatchedDatePickerOpened: Boolean = false,
    val isClosed: Boolean = false,
){

    data class UpdateTvData(
        val id: Int,
        val name: String,
        val lastWatchedSeason: TextFieldValue,
        val lastWatchedEpisode: TextFieldValue,
        val lastWatchedTime: Long?,
        val lastWatchedTimeUIText: TextFieldValue,
        val imgSource: ImageHolder?,
    )
}
