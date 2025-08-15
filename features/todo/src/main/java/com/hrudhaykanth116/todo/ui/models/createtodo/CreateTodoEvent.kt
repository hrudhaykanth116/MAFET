package com.hrudhaykanth116.todo.ui.models.createtodo

import androidx.compose.ui.text.input.TextFieldValue

sealed interface CreateTodoEvent{

    data class TitleChanged(val textFieldValue: TextFieldValue): CreateTodoEvent
    data class DescriptionChanged(val textFieldValue: TextFieldValue): CreateTodoEvent
    data class CategoryChanged(val textFieldValue: TextFieldValue): CreateTodoEvent
    data class PriorityChanged(val priority: Int): CreateTodoEvent
    data object OnTargetTimeDateTimePickerCloseRequest: CreateTodoEvent
    data class OnTargetTimeChanged(val timeMillis: Long): CreateTodoEvent
    data object OnTargetFieldClicked: CreateTodoEvent
    object Submit: CreateTodoEvent
    object UserMessageShown: CreateTodoEvent

}