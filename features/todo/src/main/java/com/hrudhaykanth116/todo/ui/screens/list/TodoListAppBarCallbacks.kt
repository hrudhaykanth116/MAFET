package com.hrudhaykanth116.todo.ui.screens.list

import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenMenuItem
import com.hrudhaykanth116.todo.data.models.TodoListScreenSortItem

data class TodoListAppBarCallbacks(
    // TODO: Multiple filters
    val onCategorySelected: (String) -> Unit = {},
    val onClearFilterClicked: () -> Unit = {},
    val onMenuItemSelected: (TodoListScreenMenuItem) -> Unit = {},
    val onSortItemSelected: (TodoListScreenSortItem) -> Unit = {},
    val onCategoriesIconClicked: () -> Unit = {},
    val onSortIconClicked: () -> Unit = {},
    val onCategoriesDismissRequest: () -> Unit = {},
    val onSearchIconClicked: () -> Unit = {},
    val onMenuItemClicked: () -> Unit = {},
    val onBackClicked: () -> Unit = {},
)