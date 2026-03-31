package com.hrudhaykanth116.journal.ui.models.list

sealed interface JournalListScreenEvent {
    data class OnSearchTextChanged(val text: String) : JournalListScreenEvent
    data object OnSearchIconClicked : JournalListScreenEvent
    data object OnCloseSearch : JournalListScreenEvent
    data class OnEntryClicked(val entryId: String) : JournalListScreenEvent
    data class OnDeleteEntry(val entryId: String) : JournalListScreenEvent
}
