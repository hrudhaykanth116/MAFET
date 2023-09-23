package com.hrudhaykanth116.tv.ui.models.updatemytv

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

data class UpdateMyTvUIState(
    val id: Int,
    val name: UIText,
    val lastWatchedSeason: Int? = null,
    val lastWatchedEpisode: Int? = null,
    val lastWatchedSeasonEpisode: UIText,
    val lastWatchedTime: UIText,
    val imgSource: ImageHolder,
)
