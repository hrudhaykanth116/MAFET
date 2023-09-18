package com.hrudhaykanth116.tv.ui.models.home

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.tv.data.models.models.TvShowData

data class TvHomeScreenUIState(
    // TODO: Create proper fields with non nullable data
    val tvShows: List<TvShowData?>? = null,
    val userMessage: UserMessage? = null,
)