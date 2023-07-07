package com.hrudhaykanth116.todo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.utils.Logger
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState

// @Preview(showBackground = true, widthDp = 200, heightDp = 100)
@Composable
fun TodoListItemUI(
    toDoTaskUIState: ToDoTaskUIState,
    onRemoveClicked: () -> Unit
) {
    Logger.d("hrudhay_logs", ": TodoListItemUI: ${toDoTaskUIState.data.title}")

    // TODO: May be this could be hoisted.
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Color.LightGray)
            .height(if(isExpanded) 120.dp else 60.dp)
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Text(text = toDoTaskUIState.data.title.text)
        IconButton(
            modifier = Modifier.padding(8.dp),
            onClick = {
                isExpanded = !isExpanded
            }
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.ArrowUpward else Icons.Default.ArrowDropDown,
                contentDescription = "Expand",
                tint = Color.Red,
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