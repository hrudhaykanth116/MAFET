package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.compose.isBlank
import com.hrudhaykanth116.core.common.utils.compose.modifier.largeRadialBackground
import com.hrudhaykanth116.core.common.utils.compose.modifier.screenBackground
import com.hrudhaykanth116.core.common.utils.functions.TextFieldChangedHandler
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.todo.R
import com.hrudhaykanth116.core.R as CoreR
import com.hrudhaykanth116.todo.ui.components.ListItemsUI
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreenUI(
    modifier: Modifier = Modifier,
    uiState: TodoListUIState,
    onTodoTitleChanged: TextFieldChangedHandler = {},
    onRemoveTask: (String) -> Unit = {},
    onItemClicked: (TodoUIModel) -> Unit = {},
    onCreateBtnClicked: () -> Unit = {},
    todoListAppBarCallbacks: TodoListAppBarCallbacks = TodoListAppBarCallbacks(),
) {

    // Scaffold(
    //     modifier = modifier,
    //     topBar = {
    //         TodoListAppBar(
    //             categories = uiState.filterOptions,
    //             selectedFilter =uiState.selectedFilter,
    //             isCategoriesPopUpShown = uiState.isCategoryListMenuVisible,
    //             isMenuVisible = uiState.isMenuVisible,
    //             isSortMenuVisible = uiState.isSortMenuVisible,
    //             todoListAppBarCallbacks = todoListAppBarCallbacks,
    //         )
    //     },
    // ) {
    //     ContentContainer(
    //         paddingValues = it,
    //         modifier = modifier,
    //         uiState = uiState,
    //         onRemoveTask = onRemoveTask,
    //         onItemClicked = onItemClicked,
    //         onTodoTitleChanged = onTodoTitleChanged,
    //         onCreateBtnClicked = onCreateBtnClicked
    //     )
    // }

    ContentContainer(
        modifier = modifier,
        uiState = uiState,
        onRemoveTask = onRemoveTask,
        onItemClicked = onItemClicked,
        onTodoTitleChanged = onTodoTitleChanged,
        onCreateBtnClicked = onCreateBtnClicked,
        todoListAppBarCallbacks = todoListAppBarCallbacks,
    )

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ContentContainer(
    modifier: Modifier = Modifier,
    uiState: TodoListUIState,
    onRemoveTask: (String) -> Unit = {},
    onItemClicked: (TodoUIModel) -> Unit = {},
    onTodoTitleChanged: TextFieldChangedHandler = {},
    onCreateBtnClicked: () -> Unit = {},
    todoListAppBarCallbacks: TodoListAppBarCallbacks = TodoListAppBarCallbacks(),
) {
    Column(
        modifier = modifier
            .screenBackground()
    ) {

        TodoListAppBar(
            categories = uiState.filterOptions,
            selectedFilter = uiState.selectedFilter,
            isCategoriesPopUpShown = uiState.isCategoryListMenuVisible,
            isMenuVisible = uiState.isMenuVisible,
            isSortMenuVisible = uiState.isSortMenuVisible,
            todoListAppBarCallbacks = todoListAppBarCallbacks,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
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

            val tasksList = uiState.uiList
            if (tasksList.isEmpty()) {
                Text(
                    text = "No todo tasks are pending.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // SearchBar(
                //     query =,
                //     onQueryChange =,
                //     onSearch =,
                //     active =,
                //     onActiveChange =
                // ) {
                //
                // }
                ListItemsUI(
                    // TODO: Use Loaded state for non null state
                    listItems = tasksList,
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
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            // AppInputText() This is causing UI issues. Check.
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = uiState.todoTitle,
                onValueChange = onTodoTitleChanged
            )
            Spacer(modifier = Modifier.width(10.dp))
            AppClickableIcon(
                imageHolder = if (uiState.todoTitle.isBlank())
                    R.drawable.ic_new_note.toImageHolder()
                else
                    CoreR.drawable.ic_save.toImageHolder(),
                onClick = onCreateBtnClicked
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}


@MyPreview
@Composable
fun TodoListScreenUIPreview() {

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

    TodoListScreenUI(
        uiState = TodoListUIState(
            uiList = sampleTasks,
        )
    )
}