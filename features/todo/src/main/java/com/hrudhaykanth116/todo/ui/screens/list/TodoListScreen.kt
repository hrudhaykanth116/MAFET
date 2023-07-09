package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.utils.Logger
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.components.ListItemsUI
import kotlinx.coroutines.launch

private const val TAG = "TodoListScreen"

@Composable
fun TodoListScreen(
    todoListViewModel: TodoListViewModel = hiltViewModel(),
    onCreateBtnClicked: () -> Unit,
    onItemClicked: (TodoUIModel) -> Unit,
) {
    Logger.d(TAG, "TodoListScreen: ")

    // Property delegation helps us in remembering the value of state directly into the field instead
    // Remember savable saves the values over configuration changes.
    var test by rememberSaveable() {
        mutableStateOf(true)
    }

    val uiState: State<UIState<TodoListUIState>> = todoListViewModel.uiStateFlow.collectAsStateWithLifecycle()

   // val list by todoViewModel.todoList.observeAsState(listOf())
    Box(modifier = Modifier.background(color = Color.Yellow)) {

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

        ListItemsUI(
            // TODO: Use Loaded state for non null state
            list = uiState.value.contentState?.list ?: listOf(),
            listState = listState,
            modifier = Modifier.fillMaxSize(),
            onRemoveTask = { toDoTask -> todoListViewModel.removeTaskItem(toDoTask) },
            onItemClicked = onItemClicked
        )

        // Bottom icons
        Row(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {

            AnimatedVisibility(
                visible = true,
            ) {

                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Add Task"
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            FloatingActionButton(onClick = onCreateBtnClicked) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }

        }

    }
}