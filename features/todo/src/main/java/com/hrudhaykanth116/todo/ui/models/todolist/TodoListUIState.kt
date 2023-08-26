package com.hrudhaykanth116.todo.ui.models.todolist

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel

data class TodoListUIState(
    val list: List<ToDoTaskUIState> = listOf(),
    val todoTitle: TextFieldValue = TextFieldValue(),
)
