package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.todo.resources.Res
import com.hrudhaykanth116.todo.resources.todo_create_title
import com.hrudhaykanth116.todo.resources.todo_list_clear_filter
import com.hrudhaykanth116.todo.resources.todo_list_title
import com.hrudhaykanth116.todo.resources.todo_search_hint
import com.hrudhaykanth116.todo.ui.TodoUIDimens
import com.hrudhaykanth116.todo.ui.models.TodoListScreenSortItem
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenMenuItem
import mafet.core_ui.generated.resources.ic_check
import mafet.core_ui.generated.resources.ic_filter
import mafet.core_ui.generated.resources.ic_menu_vertical
import mafet.core_ui.generated.resources.ic_search
import mafet.core_ui.generated.resources.ic_sort_vertical
import org.jetbrains.compose.resources.stringResource
import mafet.core_ui.generated.resources.Res as CoreUIRes

@Composable
fun TodoListAppBar(
    categories: Set<String>,
    selectedFilter: String?,
    searchText: String = "",
    isSearchBarVisible: Boolean = false,
    isCategoriesPopUpShown: Boolean = false,
    isMenuVisible: Boolean = false,
    isSortMenuVisible: Boolean = false,
    todoListAppBarCallbacks: TodoListAppBarCallbacks,
) {
    val iconColor = Color.White

    AnimatedContent(
        targetState = isSearchBarVisible,
        transitionSpec = {
            (fadeIn() + slideInHorizontally { it / 2 }) togetherWith
                    (fadeOut() + slideOutHorizontally { -it / 2 })
        },
        label = "search_bar_transition"
    ) { showSearch ->
        if (showSearch) {
            SearchBar(
                searchText = searchText,
                onSearchTextChanged = todoListAppBarCallbacks.onSearchTextChanged,
                onCloseSearch = todoListAppBarCallbacks.onCloseSearch
            )
            AppSearchBar(
                text = searchText,
                placeHolderText = stringResource(Res.string.todo_search_hint),
                onTextChange = todoListAppBarCallbacks.onSearchTextChanged,
                onCancelled = todoListAppBarCallbacks.onCloseSearch,
                onSearch = {}
            )
        } else {
            AppToolbar(
                text = stringResource(Res.string.todo_list_title),
                onBackClicked = todoListAppBarCallbacks.onBackClicked,
                navigationIcon = {},
                actions = {
                    // Search icon
                    AppClickableIcon(
                        resource = CoreUIRes.drawable.ic_search,
                        onClick = todoListAppBarCallbacks.onSearchIconClicked,
                        iconColor = iconColor
                    )

                    // Filter by category
                    Box(modifier = Modifier) {
                        AppClickableIcon(
                            resource = CoreUIRes.drawable.ic_filter,
                            onClick = todoListAppBarCallbacks.onCategoriesIconClicked,
                            iconColor = iconColor
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
                                            modifier = Modifier.fillMaxWidth(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color(0xFF374151)
                                        )
                                    },
                                    trailingIcon = {
                                        if (it == selectedFilter) {
                                            AppIcon(
                                                resource = CoreUIRes.drawable.ic_check,
                                                tint = Color(0xFF10B981)
                                            )
                                        }
                                    },
                                    onClick = { todoListAppBarCallbacks.onCategorySelected(it) },
                                )
                            }
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(Res.string.todo_list_clear_filter),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFFEF4444)
                                    )
                                },
                                onClick = { todoListAppBarCallbacks.onClearFilterClicked() },
                            )
                        }
                    }

                    // Sort
                    Box(modifier = Modifier) {
                        AppClickableIcon(
                            resource = CoreUIRes.drawable.ic_sort_vertical,
                            onClick = todoListAppBarCallbacks.onSortIconClicked,
                            iconColor = iconColor
                        )

                        DropdownMenu(
                            expanded = isSortMenuVisible,
                            onDismissRequest = todoListAppBarCallbacks.onSortIconClicked,
                        ) {
                            TodoListScreenSortItem.entries.forEach { menuItem ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = menuItem.displayName,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color(0xFF374151)
                                        )
                                    },
                                    onClick = { todoListAppBarCallbacks.onSortItemSelected(menuItem) },
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(TodoUIDimens.SpacerLarge))

                    // Menu
                    Box(modifier = Modifier) {
                        AppClickableIcon(
                            resource = CoreUIRes.drawable.ic_menu_vertical,
                            onClick = todoListAppBarCallbacks.onMenuItemClicked,
                            iconColor = iconColor
                        )
                        DropdownMenu(
                            expanded = isMenuVisible,
                            onDismissRequest = todoListAppBarCallbacks.onMenuItemClicked,
                        ) {
                            TodoListScreenMenuItem.entries.forEach { menuItem ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = menuItem.displayName,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color(0xFF374151)
                                        )
                                    },
                                    onClick = { todoListAppBarCallbacks.onMenuItemSelected(menuItem) },
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onCloseSearch: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.White.copy(alpha = 0.95f),
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                keyboardController?.hide()
                onCloseSearch()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Close search",
                    tint = Color(0xFF3B82F6)
                )
            }

            TextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(
                        text = stringResource(Res.string.todo_search_hint),
                        color = Color(0xFF9CA3AF),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFF111827),
                    unfocusedTextColor = Color(0xFF111827),
                    cursorColor = Color(0xFF3B82F6)
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { keyboardController?.hide() }
                )
            )

            AnimatedVisibility(
                visible = searchText.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = { onSearchTextChanged("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        tint = Color(0xFF6B7280)
                    )
                }
            }
        }
    }
}
