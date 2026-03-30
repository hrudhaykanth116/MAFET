package com.hrudhaykanth116.tv.ui.models.updatemytv

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.tv.data.datasources.local.models.WatchStatus

data class UpdateMyTvUIStateActual(
    val updateTvData: UpdateTvData? = null,
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
        val status: WatchStatus = WatchStatus.WATCHING,
        val rating: Int? = null,
        val notes: TextFieldValue = TextFieldValue(""),
    )
}
