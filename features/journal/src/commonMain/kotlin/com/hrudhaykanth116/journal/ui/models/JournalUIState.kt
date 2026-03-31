package com.hrudhaykanth116.journal.ui.models

import androidx.compose.ui.text.input.TextFieldValue

data class JournalUIState(
    val journalText: TextFieldValue = TextFieldValue(),
    val isSaving: Boolean = false
)
