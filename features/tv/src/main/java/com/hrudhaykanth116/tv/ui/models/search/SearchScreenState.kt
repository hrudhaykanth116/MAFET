package com.hrudhaykanth116.tv.ui.models.search

import com.hrudhaykanth116.core.ui.models.UserMessage

data class SearchScreenState(
    val query: String,
    val isLoading: Boolean = false,
    val searchResults: List<SearchScreenItemUIState> = emptyList(),
    val userMessage: UserMessage? = null,
)