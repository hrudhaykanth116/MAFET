package com.hrudhaykanth116.todo.ui.models

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.todo.domain.model.TaskCategory

data class TodoUIModel(
    val id: String? = null,
    val title: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),

    // TODO: Remove this unnecessary field
    val completed: Boolean = false,

    ) {
}