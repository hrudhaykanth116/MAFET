package com.hrudhaykanth116.todo.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItemsUI(
    listItems: List<ToDoTaskUIState>,
    modifier: Modifier = Modifier,
    onRemoveTask: (ToDoTaskUIState) -> Unit = {},
    onItemClicked: (TodoUIModel) -> Unit = {},
    listState: LazyListState = rememberLazyListState(),
) {

    LazyColumn(
        state = listState,
        // Adds space between items
        verticalArrangement = Arrangement.spacedBy(12.dp),
        // Adds padding to the row. Out side of the list item.
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier.padding(top = 8.dp)
    ) {

        items(listItems) { toDoTaskUIState ->
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