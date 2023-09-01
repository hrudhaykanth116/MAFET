package com.hrudhaykanth116.todo.ui.screens.list

import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenMenuItem

data class TodoListAppBarCallbacks(
    val onCategorySelected: (String) -> Unit = {},
    val onMenuItemSelected: (TodoListScreenMenuItem) -> Unit = {},
    val onCategoriesIconClicked: () -> Unit = {},
    val onCategoriesDismissRequest: () -> Unit = {},
    val onSearchIconClicked: () -> Unit = {},
    val onMenuItemClicked: () -> Unit = {},
    val onBackClicked: () -> Unit = {},
)