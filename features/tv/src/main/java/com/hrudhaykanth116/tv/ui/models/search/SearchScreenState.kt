package com.hrudhaykanth116.tv.ui.models.search

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.data.models.UIText

data class SearchScreenState(
    val query: String,
    val isLoading: Boolean = false,
    val searchResults: List<SearchScreenItemUIState> = emptyList(),
    val userMessage: UserMessage? = null,
)