package com.hrudhaykanth116.todo.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.todo.ui.TodoUIDimens
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

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
    var isItemAdded by mutableStateOf(listItems.size > currentSize)

    LaunchedEffect(isItemAdded) {
        if (isItemAdded) {
            listState.animateScrollToItem(listItems.size)
            currentSize = listItems.size
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(TodoUIDimens.ListItemSpacing),
        contentPadding = PaddingValues(
            horizontal = TodoUIDimens.SpacerXLarge,
            vertical = TodoUIDimens.SpacerLarge
        ),
    ) {
        itemsIndexed(
            items = listItems,
            key = { _, item -> item.data.id ?: "" }
        ) { index, toDoTaskUIState ->
            TodoListItemUI(
                modifier = Modifier.animateItem(
                    fadeInSpec = spring(stiffness = Spring.StiffnessLow),
                    fadeOutSpec = spring(stiffness = Spring.StiffnessLow),
                    placementSpec = spring(stiffness = Spring.StiffnessLow)
                ),
                toDoTaskUIState = toDoTaskUIState,
                onRemoveClicked = {
                    toDoTaskUIState.data.id?.let { onRemoveTask(it) }
                },
                onItemClicked = {
                    onItemClicked(toDoTaskUIState.data)
                }
            )
        }
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
        )
    )

    ListItemsUI(listItems = sampleTasks)
}
