package com.hrudhaykanth116.mafet.todo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.mafet.todo.data.dummydata.DummyTodoList
import com.hrudhaykanth116.mafet.todo.data.models.ToDoTask

// @Preview(showBackground = true, showSystemUi = true)
@Composable
fun TodoList(
    list: List<ToDoTask> = DummyTodoList.todoList,
    onRemoveTask: (ToDoTask) -> Unit,
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier
) {

    LazyColumn(
        state = listState,
        // Adds space between items
        verticalArrangement = Arrangement.spacedBy(8.dp),
        // Adds padding to the row. Out side of the list item.
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.background(color = Color.Blue)
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
        ) { toDoTask: ToDoTask ->
            Row(
                // Modifier.animateItemPlacement(tween(1000))
            ) {
                TodoListItem(toDoTask = toDoTask, onRemoveClicked = { onRemoveTask(toDoTask) })
            }
        }
    }
}