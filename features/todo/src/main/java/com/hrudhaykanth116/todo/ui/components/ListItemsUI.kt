package com.hrudhaykanth116.todo.ui.components

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItemsUI(
    listItems: List<ToDoTaskUIState>,
    modifier: Modifier = Modifier,
    onRemoveTask: (String) -> Unit = {},
    onItemClicked: (TodoUIModel) -> Unit = {},
    listState: LazyListState = rememberLazyListState(),
) {

    var currentSize by rememberSaveable { mutableStateOf(listItems.size) }
    // val isItemAdded = listItems.size > currentSize
    var isItemAdded by mutableStateOf(listItems.size > currentSize)

    LaunchedEffect(isItemAdded) { //Won't be called upon item deletion
        if (isItemAdded) {
            listState.animateScrollToItem(listItems.size)
            currentSize = listItems.size
        }
    }

    LazyColumn(
        state = listState,
        // Adds space between items
        verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
        // Adds padding to the row. Out side of the list item.
        contentPadding = PaddingValues(horizontal = Dimens.DEFAULT_PADDING),
    ) {

        items(listItems) { toDoTaskUIState ->
            TodoListItemUI(
                modifier = Modifier.clickable {
                    onItemClicked(toDoTaskUIState.data)
                },
                toDoTaskUIState = toDoTaskUIState,
                onRemoveClicked = {
                    // Id will be null if newly creating only.
                    onRemoveTask(toDoTaskUIState.data.id!!)
                }
            )
        }

        // Create grouped list in viewmodel!!.
        // val groupedTodoList: Map<String, List<ToDoTaskUIState>> = listItems.groupBy { it.data.category.text }

        // Grouped list ui.
        /*
        groupedTodoList.forEach { (groupName, groupList) ->
            // item {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Yellow)
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = groupName,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            items(groupList) { toDoTaskUIState ->
                TodoListItemUI(
                    modifier = Modifier.clickable {
                        onItemClicked(toDoTaskUIState.data)
                    },
                    toDoTaskUIState = toDoTaskUIState,
                    onRemoveClicked = {
                        onRemoveTask(toDoTaskUIState)
                    }
                )
            }
        }*/
    }
}


@MyPreview
@Composable
fun ListItemUIPreview() {
    ListItemsUI(
        listItems = listOf(
            ToDoTaskUIState(data = TodoUIModel()),
            ToDoTaskUIState(data = TodoUIModel()),
            ToDoTaskUIState(data = TodoUIModel()),
        )
    )
}