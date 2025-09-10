package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenMenuItem
import com.hrudhaykanth116.todo.data.models.TodoListScreenSortItem

@Composable
fun TodoListAppBar(
    categories: Set<String>,
    selectedFilter: String?,
    isCategoriesPopUpShown: Boolean = false,
    isMenuVisible: Boolean = false,
    isSortMenuVisible: Boolean = false,
    todoListAppBarCallbacks: TodoListAppBarCallbacks,
) {

    AppToolbar(
        text = "Pending tasks",
        onBackClicked = todoListAppBarCallbacks.onBackClicked,
        navigationIcon = {},
        actions = {

            Box(modifier = Modifier) {
                AppClickableIcon(
                    resId = R.drawable.ic_filter,
                    onClick = todoListAppBarCallbacks.onCategoriesIconClicked
                )
                DropdownMenu(
                    expanded = isCategoriesPopUpShown,
                    onDismissRequest = todoListAppBarCallbacks.onCategoriesDismissRequest,
                ) {
                    categories.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    // .background(MaterialTheme.colorScheme.surfaceVariant)
                                )
                            },
                            trailingIcon = { if(it == selectedFilter) AppIcon(resId = R.drawable.ic_check, tint = Color.Unspecified) },
                            onClick = { todoListAppBarCallbacks.onCategorySelected(it) },
                        )
                    }
                    DropdownMenuItem(
                        text = { Text(text = "Clear filter") },
                        onClick = { todoListAppBarCallbacks.onClearFilterClicked() },
                    )
                }
            }

            Box(modifier = Modifier) {
                AppClickableIcon(
                    resId = R.drawable.ic_sort_vertical,
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
                    resId = R.drawable.ic_menu_vertical,
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