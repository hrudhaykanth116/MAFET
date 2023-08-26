package com.hrudhaykanth116.todo.ui.models.todolist

import androidx.compose.ui.text.input.TextFieldValue

sealed interface TodoListScreenEvent {

    data class RemoveTask(val id: String): TodoListScreenEvent

    data class TodoTaskTitleChanged(val textFieldValue: TextFieldValue): TodoListScreenEvent

    data class CreateTodoTask(val taskTitle: String): TodoListScreenEvent

}