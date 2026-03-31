package com.hrudhaykanth116.journal.ui.models

import androidx.compose.ui.text.input.TextFieldValue

sealed interface JournalScreenEvent {
    data class TextChanged(val text: TextFieldValue) : JournalScreenEvent
    data object SaveClicked : JournalScreenEvent
    data object ClearClicked : JournalScreenEvent
}
