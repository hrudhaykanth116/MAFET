package com.hrudhaykanth116.mafet.todo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.mafet.todo.data.dummydata.DummyTodoList
import com.hrudhaykanth116.mafet.todo.data.models.ToDoTask

// @Preview(showBackground = true, widthDp = 200, heightDp = 100)
@Composable
fun TodoListItem(
    toDoTask: ToDoTask = DummyTodoList.todoList[0],
    onRemoveClicked: () -> Unit
) {

    // TODO: May be this could be hoisted.
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Color.LightGray)
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Text(text = toDoTask.title)
        IconButton(onClick = {
            isExpanded = !isExpanded
        }) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.ArrowUpward else Icons.Default.ArrowDropDown,
                contentDescription = "Expand",
                tint = Color.Green
            )
        }
        IconButton(onClick = onRemoveClicked) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Expand",
                tint = Color.Red
            )
        }
    }
}