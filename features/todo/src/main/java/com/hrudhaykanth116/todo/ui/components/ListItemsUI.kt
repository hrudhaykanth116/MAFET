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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItemsUI(
    listItems: ImmutableList<ToDoTaskUIState>,
    modifier: Modifier = Modifier,
    onRemoveTask: (String) -> Unit = {},
    onItemClicked: (TodoUIModel) -> Unit = {},
    listState: LazyListState = rememberLazyListState(),
) {

    var currentSize by rememberSaveable { mutableIntStateOf(listItems.size) }
    // val isItemAdded = listItems.size > currentSize
    var isItemAdded by mutableStateOf(listItems.size > currentSize)

    LaunchedEffect(isItemAdded) {
        if (isItemAdded) {
            listState.animateScrollToItem(listItems.size)
            currentSize = listItems.size
        }
    }

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
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

    val sampleTasks = persistentListOf(
        ToDoTaskUIState(
            data = TodoUIModel(
                id = "1",
                title = TextFieldValue("Buy groceries"),
                description = TextFieldValue("Milk, Eggs, Bread, and Coffee"),
                category = TextFieldValue("Shopping"),
                priority = 2,
                targetTime = TextFieldValue("2025-07-25 10:00 AM")
            )
        ),
        ToDoTaskUIState(
            data = TodoUIModel(
                id = "2",
                title = TextFieldValue("Morning Workout"),
                description = TextFieldValue("Cardio and stretches"),
                category = TextFieldValue("Health"),
                priority = 4,
                targetTime = TextFieldValue("2025-07-24 06:30 AM")
            ),
        ),
        ToDoTaskUIState(
            data = TodoUIModel(
                id = "3",
                title = TextFieldValue("Project Meeting"),
                description = TextFieldValue("Weekly sync-up with dev team"),
                category = TextFieldValue("Work"),
                priority = 5,
                targetTime = TextFieldValue("2025-07-24 11:00 AM")
            )
        ),
        ToDoTaskUIState(
            data = TodoUIModel(
                id = "4",
                title = TextFieldValue("Read Book"),
                description = TextFieldValue("Read 50 pages of 'Atomic Habits'"),
                category = TextFieldValue("Personal Development"),
                priority = 3,
                targetTime = TextFieldValue("2025-07-24 08:00 PM")
            ),
        )
    )

    ListItemsUI(
        listItems = sampleTasks
    )
}