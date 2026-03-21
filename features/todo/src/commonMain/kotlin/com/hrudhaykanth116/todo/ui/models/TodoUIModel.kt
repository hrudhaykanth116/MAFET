package com.hrudhaykanth116.todo.ui.models

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.todo.domain.model.TaskCategory

data class TodoUIModel(
    val id: String? = null,
    val title: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),
    val category: TextFieldValue = TextFieldValue(TaskCategory.GENERAL.key),
    val priority: Int = 3, // 1..5 (Medium by default)

    val targetTime: TextFieldValue = TextFieldValue(),

    // hrudhay_check_list: Remove this unnecessary field
    val completed: Boolean = false,
)