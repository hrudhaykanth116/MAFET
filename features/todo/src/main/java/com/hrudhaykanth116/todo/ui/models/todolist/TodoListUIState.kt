package com.hrudhaykanth116.todo.ui.models.todolist

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.ui.models.BaseUIState
import com.hrudhaykanth116.core.ui.models.UIState2
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

data class TodoListUIState(
    override val uiState2: UIState2,
    val uiList: ImmutableList<ToDoTaskUIState> = persistentListOf(),
    // TODO: Have some enum class of list.
    val filterOptions: ImmutableSet<String> = persistentSetOf(),
    val selectedFilter: String? = null,
    val todoTitle: TextFieldValue = TextFieldValue(),

    val itemPosition: Int = 0,
    val search: String = "",

    val isSearchBarVisible: Boolean = false,
    val isCategoryListMenuVisible: Boolean = false,
    val isMenuVisible: Boolean = false,

    val isSortMenuVisible: Boolean = false,
    val sortItem: TodoListScreenSortItem = TodoListScreenSortItem.PRIORITY


    ) : BaseUIState(uiState2)
