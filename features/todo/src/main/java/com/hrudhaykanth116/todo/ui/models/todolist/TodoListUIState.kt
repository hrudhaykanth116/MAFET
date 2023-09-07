package com.hrudhaykanth116.todo.ui.models.todolist

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.ui.models.BaseUIState
import com.hrudhaykanth116.core.ui.models.UIState2
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState

data class TodoListUIState(
    override val uiState2: UIState2,
    val uiList: List<ToDoTaskUIState> = listOf(),
    val categories: Set<String> = setOf(),
    val todoTitle: TextFieldValue = TextFieldValue(),

    val itemPosition: Int = 0,
    val category: String = "",
    val search: String = "",

    val isSearchBarVisible: Boolean = false,
    val isCategoryListMenuVisible: Boolean = false,
    val isMenuVisible: Boolean = false,
    val isSortMenuVisible: Boolean = false,


    ) : BaseUIState(uiState2)
