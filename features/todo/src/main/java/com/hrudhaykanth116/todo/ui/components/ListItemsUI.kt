package com.hrudhaykanth116.todo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.annotations.PreviewApi
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.todo.data.dummydata.DummyTodoList
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState

@Composable
fun ListItemsUI(
    list: List<ToDoTaskUIState>,
    modifier: Modifier = Modifier,
    onRemoveTask: (ToDoTaskUIState) -> Unit = {},
    onItemClicked: (TodoUIModel) -> Unit = {},
    listState: LazyListState = rememberLazyListState()
) {

    LazyColumn(
        state = listState,
        // Adds space between items
        verticalArrangement = Arrangement.spacedBy(12.dp),
        // Adds padding to the row. Out side of the list item.
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        item {
            Text(text = "Pending")
            // Only put divider like things because all under item block is considered as item and effect scrollTo()
            Divider()
        }
        items(
            list,
//            key = {
//                // Key helps to retain state of item/position. Works like id equals when notifyDataSetChanged.
//                it.id
//            }
        ) { toDoTaskUIState: ToDoTaskUIState ->
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
    }
}


@MyPreview
@Composable
fun ListItemUIPreview() {
    ListItemsUI(
        list = listOf(
            ToDoTaskUIState(data = TodoUIModel()),
            ToDoTaskUIState(data = TodoUIModel()),
            ToDoTaskUIState(data = TodoUIModel()),
        )
    )
}