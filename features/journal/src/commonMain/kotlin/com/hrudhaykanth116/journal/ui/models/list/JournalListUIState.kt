package com.hrudhaykanth116.journal.ui.models.list

import com.hrudhaykanth116.journal.ui.models.JournalEntryUIModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class JournalListUIState(
    val entries: ImmutableList<JournalEntryUIModel> = persistentListOf(),
    val search: String = "",
    val isSearchBarVisible: Boolean = false
)
