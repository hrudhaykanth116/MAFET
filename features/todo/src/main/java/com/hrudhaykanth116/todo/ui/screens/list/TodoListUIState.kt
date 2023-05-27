package com.hrudhaykanth116.todo.ui.screens.list

import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel

data class TodoListUIState(
    val list: List<ToDoTaskUIState> = listOf(),
)
