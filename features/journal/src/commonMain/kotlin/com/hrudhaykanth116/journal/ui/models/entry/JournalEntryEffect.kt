package com.hrudhaykanth116.journal.ui.models.entry

sealed interface JournalEntryEffect {
    data object NavigateBack : JournalEntryEffect
}
