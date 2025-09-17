package com.hrudhaykanth116.tv.ui.models.home

import com.hrudhaykanth116.core.common.ui.models.UserMessage

data class EntertainmentHomeScreenUIState(
    // hrudhay_check_list: Create proper fields with non nullable data
    val tvShows: List<MyTvUIState>? = null,
    val shouldNavigateToSearchScreen: Boolean = false,
    val userMessage: UserMessage? = null,
    val updateTv: MyTvUIState? = null,
)