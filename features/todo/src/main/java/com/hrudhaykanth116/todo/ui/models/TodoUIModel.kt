package com.hrudhaykanth116.todo.ui.models

import androidx.compose.ui.text.input.TextFieldValue

data class TodoUIModel(
    val id: String? = null,
    val title: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),
    val category: TextFieldValue = TextFieldValue(),
    val priority: Int = 3, // 1..5

    val targetTime: TextFieldValue = TextFieldValue(),

    // TODO: Remove this unnecessary field
    val completed: Boolean = false,

    ) {
}