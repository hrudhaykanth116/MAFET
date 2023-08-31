package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.color.ColorParser
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.functions.TextFieldChangedHandler
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.models.UIState2
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.todo.R
import com.hrudhaykanth116.todo.ui.components.ListItemsUI
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState
import com.hrudhaykanth116.core.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreenUI(
    modifier: Modifier = Modifier,
    uiState: TodoListUIState,
    onTodoTitleChanged: TextFieldChangedHandler = {},
    onRemoveTask: (ToDoTaskUIState) -> Unit = {},
    onItemClicked: (TodoUIModel) -> Unit = {},
    onCreateBtnClicked: () -> Unit = {},
    todoListAppBarCallbacks: TodoListAppBarCallbacks = TodoListAppBarCallbacks(),
) {

    Scaffold(
        modifier = Modifier.background(color = Color.Yellow),
        topBar = {
            TodoListAppBar(
                categories = uiState.categories,
                isCategoriesPopUpShown = uiState.isCategoryListMenuVisible,
                isMenuVisible = uiState.isMenuVisible,
                todoListAppBarCallbacks = todoListAppBarCallbacks,
            )
        },
    ) {
        ContentContainer(
            paddingValues = it,
            modifier = modifier,
            uiState = uiState,
            onRemoveTask = onRemoveTask,
            onItemClicked = onItemClicked,
            onTodoTitleChanged = onTodoTitleChanged,
            onCreateBtnClicked = onCreateBtnClicked
        )
    }

}

@Composable
private fun TodoListAppBar(
    categories: Set<String>,
    isCategoriesPopUpShown: Boolean = false,
    isMenuVisible: Boolean = false,
    todoListAppBarCallbacks: TodoListAppBarCallbacks,
) {

    AppToolbar(
        text = "All category",
        onBackClicked = todoListAppBarCallbacks.onBackClicked,
        actions = {

            Box(modifier = Modifier) {
                AppClickableIcon(
                    imageHolder = CoreR.drawable.ic_expand_arrow.toImageHolder(),
                    iconColor = Color.White,
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


            AppClickableIcon(
                imageHolder = CoreR.drawable.ic_search.toImageHolder(),
                iconColor = Color.White,
                onClick = todoListAppBarCallbacks.onSearchIconClicked
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier) {
                AppClickableIcon(
                    imageHolder = CoreR.drawable.ic_menu_vertical.toImageHolder(),
                    iconColor = Color.White,
                    onClick = todoListAppBarCallbacks.onMenuItemClicked
                )
                DropdownMenu(
                    expanded = false,
                    onDismissRequest = todoListAppBarCallbacks.onMenuItemClicked,
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Settings") },
                        onClick = { },
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Clear all") },
                        onClick = { },
                    )
                }
            }
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ContentContainer(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: TodoListUIState,
    onRemoveTask: (ToDoTaskUIState) -> Unit = {},
    onItemClicked: (TodoUIModel) -> Unit = {},
    onTodoTitleChanged: TextFieldChangedHandler = {},
    onCreateBtnClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .background(color = Color.Green)
            .padding(paddingValues)
    ) {
        Box(
            modifier = Modifier
                .background(ColorParser.parseHexCode(0xFF84eaf5))
                // .background(Color.Cyan)
                // .gradientBackground(
                //     listOf(
                //         Color.Blue,
                //         Color.Cyan
                //     )
                // )
                .fillMaxSize()
                .weight(1f)
        ) {

            val coroutineScope = rememberCoroutineScope()

            // Check state hoisting.
            val listState = rememberLazyListState()

            // Show the button if the first visible item is past
            // the first item. We use a remembered derived` state to
            // minimize unnecessary compositions
            val shouldShowScrollToTopBtn by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0
                }
            }

            val tasksList = uiState.uiList
            if (tasksList.isEmpty()) {
                Text(
                    text = "No todo tasks are pending.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                ListItemsUI(
                    // TODO: Use Loaded state for non null state
                    listItems = tasksList,
                    listState = listState,
                    modifier = Modifier.fillMaxSize(),
                    onRemoveTask = onRemoveTask,
                    onItemClicked = onItemClicked
                )
            }

            // Bottom icons
            // Row(
            //     modifier = Modifier.align(Alignment.BottomEnd)
            // ) {
            //
            //     AnimatedVisibility(
            //         visible = shouldShowScrollToTopBtn,
            //     ) {
            //
            //         FloatingActionButton(onClick = {
            //             coroutineScope.launch {
            //                 listState.animateScrollToItem(0)
            //             }
            //         }) {
            //             Icon(
            //                 imageVector = Icons.Default.ArrowUpward,
            //                 contentDescription = "Add Task"
            //             )
            //         }
            //     }
            //
            //     Spacer(modifier = Modifier.width(8.dp))
            //
            //     FloatingActionButton(onClick = onCreateBtnClicked) {
            //         Icon(
            //             imageVector = Icons.Default.Add,
            //             contentDescription = "Add Task"
            //         )
            //     }
            //
            // }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // AppInputText() This is causing UI issues. Check.
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = uiState.todoTitle,
                onValueChange = onTodoTitleChanged
            )
            Spacer(modifier = Modifier.width(10.dp))
            AppClickableIcon(
                imageHolder = R.drawable.ic_note_save.toImageHolder(),
                iconColor = Color.Green,
                // iconBackgroundColor = Color.LightGray,
                onClick = onCreateBtnClicked
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


@MyPreview
@Composable
fun TodoListScreenUIPreview() {
    TodoListScreenUI(
        uiState = TodoListUIState(UIState2.Idle)
    )
}