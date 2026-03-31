package com.hrudhaykanth116.journal.ui.models.list

sealed interface JournalListEffect {
    data class NavigateToEntry(val entryId: String) : JournalListEffect
    data object NavigateToCreateEntry : JournalListEffect
}
