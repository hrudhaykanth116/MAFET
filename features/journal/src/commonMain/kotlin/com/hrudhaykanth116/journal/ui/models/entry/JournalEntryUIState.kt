package com.hrudhaykanth116.journal.ui.models.entry

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class JournalEntryUIState(
    val id: String? = null,
    val title: TextFieldValue = TextFieldValue(),
    val body: TextFieldValue = TextFieldValue(),
    val mood: Int = 3,
    val tags: ImmutableList<String> = persistentListOf(),
    val tagInput: TextFieldValue = TextFieldValue(),
    val selectedDate: Long? = null,
    val formattedDate: String = "",
    val isDatePickerVisible: Boolean = false,
    val isEditMode: Boolean = false,
    val isSaving: Boolean = false,
    val isLoading: Boolean = false
)
