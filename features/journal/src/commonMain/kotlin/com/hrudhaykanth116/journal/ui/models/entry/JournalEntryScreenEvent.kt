package com.hrudhaykanth116.journal.ui.models.entry

import androidx.compose.ui.text.input.TextFieldValue

sealed interface JournalEntryScreenEvent {
    data class OnTitleChanged(val title: TextFieldValue) : JournalEntryScreenEvent
    data class OnBodyChanged(val body: TextFieldValue) : JournalEntryScreenEvent
    data class OnMoodChanged(val mood: Int) : JournalEntryScreenEvent
    data class OnTagInputChanged(val tagInput: TextFieldValue) : JournalEntryScreenEvent
    data object OnAddTag : JournalEntryScreenEvent
    data class OnRemoveTag(val tag: String) : JournalEntryScreenEvent
    data object OnDateFieldClicked : JournalEntryScreenEvent
    data class OnDateSelected(val dateMillis: Long?) : JournalEntryScreenEvent
    data object OnDatePickerDismissed : JournalEntryScreenEvent
    data object OnSaveClicked : JournalEntryScreenEvent
}
