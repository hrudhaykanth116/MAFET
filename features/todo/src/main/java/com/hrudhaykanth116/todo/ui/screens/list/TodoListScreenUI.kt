package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.common.utils.color.ColorParser
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.functions.TextFieldChangedHandler
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.todo.R
import com.hrudhaykanth116.todo.ui.components.ListItemsUI
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState

@Composable
fun TodoListScreenUI(
    modifier: Modifier = Modifier,
    uiState: UIState<TodoListUIState>,
    onTodoTitleChanged: TextFieldChangedHandler = {},
    onRemoveTask: (ToDoTaskUIState) -> Unit = {},
    onItemClicked: (TodoUIModel) -> Unit = {},
    onCreateBtnClicked: () -> Unit = {},
) {

    Column {
        Box(
            modifier = modifier
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

            val tasksList = uiState.contentState?.list
            if (tasksList.isNullOrEmpty()) {
                Text(
                    text = "No todo tasks are pending.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                ListItemsUI(
                    // TODO: Use Loaded state for non null state
                    list = tasksList,
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
        Row {
            AppInputText(
                modifier = Modifier.weight(1f),
                onInputChange = onTodoTitleChanged
            )
            AppClickableIcon(
                imageHolder = R.drawable.ic_delete.toImageHolder(),
                onClick = onCreateBtnClicked
            )
        }
    }

}

@MyPreview
@Composable
fun TodoListScreenUIPreview() {
    TodoListScreenUI(
        uiState = UIState.Loaded()
    )
}