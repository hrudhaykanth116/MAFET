package com.hrudhaykanth116.todo.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel

// @Preview(showBackground = true, widthDp = 200, heightDp = 100)
@Composable
fun TodoListItemUI(
    toDoTaskUIState: ToDoTaskUIState,
    onRemoveClicked: () -> Unit = {}
) {
    Logger.d("hrudhay_logs", ": TodoListItemUI: ${toDoTaskUIState.data.title}")

    // TODO: May be this could be hoisted. Savable because, state retained on scroll or more.
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val height by animateDpAsState(
        if (isExpanded) 120.dp else 60.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Color.Cyan)
            .height(
                height.coerceAtLeast(60.dp) // Makes sure atleast 60dp is set.
            )
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

@Preview
@Composable
fun TodoListItemUIPreview(){
    TodoListItemUI(
        toDoTaskUIState = ToDoTaskUIState(
            TodoUIModel(
                "1",
                TextFieldValue("Title"),
                TextFieldValue("Description"),
            )
        )
    )
}