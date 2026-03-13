package com.hrudhaykanth116.todo.ui.models.createtodo

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.todo.domain.model.TaskCategory

sealed interface CreateTodoEvent{

    data class TitleChanged(val textFieldValue: TextFieldValue): CreateTodoEvent
    data class DescriptionChanged(val textFieldValue: TextFieldValue): CreateTodoEvent
    data class CategoryChanged(val textFieldValue: TextFieldValue): CreateTodoEvent
    data class CategorySelected(val category: TaskCategory): CreateTodoEvent
    data class PriorityChanged(val priority: Int): CreateTodoEvent
    data object OnTargetTimeDateTimePickerCloseRequest: CreateTodoEvent
    data class OnTargetTimeChanged(val timeMillis: Long): CreateTodoEvent
    data object OnTargetFieldClicked: CreateTodoEvent
    data object OnCategoryFieldClicked: CreateTodoEvent
    data object OnCategoryDismissRequest: CreateTodoEvent
    object Submit: CreateTodoEvent
    object UserMessageShown: CreateTodoEvent

}