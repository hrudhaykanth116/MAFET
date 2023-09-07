package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenMenuItem
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenSortItem

@Composable
fun TodoListAppBar(
    categories: Set<String>,
    isCategoriesPopUpShown: Boolean = false,
    isMenuVisible: Boolean = false,
    isSortMenuVisible: Boolean = false,
    todoListAppBarCallbacks: TodoListAppBarCallbacks,
) {

    AppToolbar(
        text = "All category",
        onBackClicked = todoListAppBarCallbacks.onBackClicked,
        navigationIcon = {},
        actions = {

            Box(modifier = Modifier) {
                AppClickableIcon(
                    imageHolder = R.drawable.ic_filter.toImageHolder(),
                    onClick = todoListAppBarCallbacks.onCategoriesIconClicked
                )
                DropdownMenu(
                    expanded = isCategoriesPopUpShown,
                    onDismissRequest = todoListAppBarCallbacks.onCategoriesDismissRequest,
                ) {
                    categories.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it) },
                            onClick = { todoListAppBarCallbacks.onCategorySelected(it) },
                        )
                    }
                }
            }

            Box(modifier = Modifier) {
                AppClickableIcon(
                    imageHolder = R.drawable.ic_sort_vertical.toImageHolder(),
                    onClick = todoListAppBarCallbacks.onSortIconClicked
                )

                DropdownMenu(
                    expanded = isSortMenuVisible,
                    onDismissRequest = todoListAppBarCallbacks.onSortIconClicked,
                ) {
                    TodoListScreenSortItem.values().forEach { menuItem ->
                        val onClick: () -> Unit =
                            { todoListAppBarCallbacks.onSortItemSelected(menuItem) }
                        DropdownMenuItem(
                            text = { Text(text = menuItem.displayName) },
                            onClick = onClick,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier) {
                AppClickableIcon(
                    imageHolder = R.drawable.ic_menu_vertical.toImageHolder(),
                    onClick = todoListAppBarCallbacks.onMenuItemClicked
                )
                DropdownMenu(
                    expanded = isMenuVisible,
                    onDismissRequest = todoListAppBarCallbacks.onMenuItemClicked,
                ) {
                    TodoListScreenMenuItem.values().forEach { menuItem ->
                        val onClick: () -> Unit =
                            { todoListAppBarCallbacks.onMenuItemSelected(menuItem) }
                        DropdownMenuItem(
                            text = { Text(text = menuItem.displayName) },
                            onClick = onClick,
                        )
                    }

                }
            }
        }
    )
}