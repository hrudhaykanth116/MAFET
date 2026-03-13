package com.hrudhaykanth116.todo.ui.screens.list

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ads.BannerAd
import com.hrudhaykanth116.core.ads.TestAdUnitIds
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.compose.modifier.screenBackground
import com.hrudhaykanth116.core.common.utils.functions.TextFieldChangedHandler
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.todo.R
import com.hrudhaykanth116.todo.ui.TodoUIDimens
import com.hrudhaykanth116.todo.ui.components.ListItemsUI
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState
import ir.kaaveh.sdpcompose.sdp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

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
    val tasksList = uiState.uiList
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val shouldShowScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 2 }
    }

    // Handle back button when search is visible
    BackHandler(enabled = uiState.isSearchBarVisible) {
        todoListAppBarCallbacks.onCloseSearch()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .screenBackground()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(bottom = if (uiState.isSearchBarVisible) 0.dp else 16.dp)
        ) {
            TodoListAppBar(
                categories = uiState.filterOptions,
                selectedFilter = uiState.selectedFilter,
                searchText = uiState.search,
                isSearchBarVisible = uiState.isSearchBarVisible,
                isCategoriesPopUpShown = uiState.isCategoryListMenuVisible,
                isMenuVisible = uiState.isMenuVisible,
                isSortMenuVisible = uiState.isSortMenuVisible,
                todoListAppBarCallbacks = todoListAppBarCallbacks,
            )

            Box(modifier = Modifier.weight(1f)) {
                if (tasksList.isEmpty()) {
                    EmptyStateContent()
                } else {
                    ListItemsUI(
                        listItems = tasksList,
                        listState = listState,
                        modifier = Modifier.fillMaxSize(),
                        onRemoveTask = onRemoveTask,
                        onItemClicked = onItemClicked
                    )
                }
            }

            if (!uiState.isSearchBarVisible) {
                BottomInputSection(
                    todoTitle = uiState.todoTitle,
                    onTodoTitleChanged = onTodoTitleChanged,
                    onCreateBtnClicked = onCreateBtnClicked
                )

                if (uiState.showAd) {
                    BannerAd(
                        adUnitId = TestAdUnitIds.ADAPTIVE_BANNER,
                        modifier = Modifier.fillMaxWidth()
                    )
                    VerticalSpacer(height = TodoUIDimens.SpacerMedium)
                }
            }
        }

        // Scroll to top FAB
        AnimatedVisibility(
            visible = shouldShowScrollToTop,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 180.dp),
            enter = fadeIn(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ) + scaleIn(
                initialScale = 0.3f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
            exit = fadeOut(
                animationSpec = spring(stiffness = Spring.StiffnessHigh)
            ) + scaleOut(
                targetScale = 0.3f,
                animationSpec = spring(stiffness = Spring.StiffnessHigh)
            )
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.size(56.dp),
                containerColor = Color(0xFF3B82F6),
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Scroll to top",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun EmptyStateContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(TodoUIDimens.EmptyStatePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(TodoUIDimens.EmptyStateIconSize)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.TaskAlt,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.todo_empty_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(TodoUIDimens.SpacerMedium))

        Text(
            text = stringResource(R.string.todo_empty_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BottomInputSection(
    todoTitle: TextFieldValue,
    onTodoTitleChanged: TextFieldChangedHandler,
    onCreateBtnClicked: () -> Unit
) {
    val hasText = todoTitle.text.isNotBlank()
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    // Handle back press when text field is focused or has text
    BackHandler(enabled = isFocused || hasText) {
        if (hasText) {
            onTodoTitleChanged(TextFieldValue(""))
        }
        focusManager.clearFocus()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.sdp),
        shape = RoundedCornerShape(TodoUIDimens.InputCornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.sdp,
                    end = 8.sdp,
                    top = 2.sdp,
                    bottom = 2.sdp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = todoTitle,
                onValueChange = { newText: TextFieldValue ->
                    if (newText.text.lines().size <= 2) {
                        onTodoTitleChanged(newText)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                placeholder = {
                    Text(
                        text = stringResource(R.string.todo_list_input_hint),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                },
                singleLine = false,
                minLines = 2,
                maxLines = 2,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.width(8.dp))

            FloatingActionButton(
                onClick = onCreateBtnClicked,
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 2.dp
                )
            ) {
                AnimatedContent(
                    targetState = hasText,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(300)) +
                         scaleIn(initialScale = 0.8f, animationSpec = tween(300)))
                            .togetherWith(
                                fadeOut(animationSpec = tween(300)) +
                                scaleOut(targetScale = 0.8f, animationSpec = tween(300))
                            )
                    },
                    label = "icon_animation"
                ) { showSaveIcon ->
                    if (showSaveIcon) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save task",
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.todo_content_desc_add),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
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
                targetTime = TextFieldValue("Tomorrow 10:00 AM")
            )
        ),
        ToDoTaskUIState(
            data = TodoUIModel(
                id = "2",
                title = TextFieldValue("Morning Workout"),
                description = TextFieldValue("Cardio and stretches"),
                category = TextFieldValue("Health"),
                priority = 4,
                targetTime = TextFieldValue("Today 6:30 AM")
            )
        ),
        ToDoTaskUIState(
            data = TodoUIModel(
                id = "3",
                title = TextFieldValue("Project Meeting"),
                description = TextFieldValue("Weekly sync-up with dev team"),
                category = TextFieldValue("Work"),
                priority = 5,
                targetTime = TextFieldValue("Today 11:00 AM")
            )
        )
    )

    TodoListScreenUI(
        uiState = TodoListUIState(uiList = sampleTasks)
    )
}

@MyPreview
@Composable
fun TodoListScreenUIEmptyPreview() {
    TodoListScreenUI(
        uiState = TodoListUIState()
    )
}
