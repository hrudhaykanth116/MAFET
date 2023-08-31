package com.hrudhaykanth116.todo.ui.screens.list

data class TodoListAppBarCallbacks(
    val onCategorySelected: (String) -> Unit = {},
    val onCategoriesIconClicked: () -> Unit = {},
    val onCategoriesDismissRequest: () -> Unit = {},
    val onSearchIconClicked: () -> Unit = {},
    val onMenuItemClicked: () -> Unit = {},
    val onBackClicked: () -> Unit = {},
)