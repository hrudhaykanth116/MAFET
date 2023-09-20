package com.hrudhaykanth116.tv.ui.models.home

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.tv.ui.models.search.MyTvUIState

data class TvHomeScreenUIState(
    // TODO: Create proper fields with non nullable data
    val tvShows: List<MyTvUIState>? = null,
    val shouldNavigateToSearchScreen: Boolean = false,
    val userMessage: UserMessage? = null,
)