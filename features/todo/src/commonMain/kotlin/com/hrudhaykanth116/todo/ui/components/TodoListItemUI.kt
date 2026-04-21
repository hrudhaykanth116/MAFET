package com.hrudhaykanth116.todo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.preview.MyPreview
import com.hrudhaykanth116.todo.resources.Res
import com.hrudhaykanth116.todo.resources.todo_content_desc_delete
import org.jetbrains.compose.resources.stringResource
import com.hrudhaykanth116.todo.ui.TodoColors
import com.hrudhaykanth116.todo.ui.TodoUIDimens
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TodoListItemUI(
    toDoTaskUIState: ToDoTaskUIState,
    modifier: Modifier = Modifier,
    onRemoveClicked: () -> Unit = {},
    onItemClicked: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val todoData = toDoTaskUIState.data
    val priorityColor = TodoColors.getPriorityColor(todoData.priority)
    val categoryColor = TodoColors.getCategoryColor(todoData.category.text)


    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onRemoveClicked()
                true
            } else {
                false
            }
        },
        positionalThreshold = { distance -> distance * 0.5f }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier.testTag("todo_item_${todoData.title.text}"),
        backgroundContent = {
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                    else -> Color.Transparent
                },
                label = "swipe_color"
            )
            val scale by animateFloatAsState(
                targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) 1f else 0.8f,
                label = "icon_scale"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(TodoUIDimens.ListItemCornerRadius))
                    .background(color)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(Res.string.todo_content_desc_delete),
                    tint = Color.White,
                    modifier = Modifier.scale(scale)
                )
            }
        },
        enableDismissFromStartToEnd = false,
    ) {
        val cardModifier = if (sharedTransitionScope != null && animatedVisibilityScope != null && todoData.id != null) {
            with(sharedTransitionScope) {
                Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "task_card_${todoData.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 300, easing = FastOutSlowInEasing)
                        }
                    )
            }
        } else {
            Modifier.fillMaxWidth()
        }

        Card(
            modifier = cardModifier,
            shape = RoundedCornerShape(TodoUIDimens.ListItemCornerRadius),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Box {
                // Priority gradient bar at top
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    priorityColor,
                                    priorityColor.copy(alpha = 0.3f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClicked() }
                        .padding(
                            top = TodoUIDimens.SpacerXLarge,
                            start = TodoUIDimens.ListItemHorizontalPadding,
                            end = TodoUIDimens.ListItemHorizontalPadding,
                            bottom = TodoUIDimens.ListItemVerticalPadding
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = todoData.title.text,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(TodoUIDimens.SpacerMedium))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(TodoUIDimens.SpacerMedium),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (todoData.category.text.isNotBlank()) {
                                    CategoryChip(
                                        category = todoData.category.text,
                                        color = categoryColor
                                    )
                                }

                                PriorityBadge(priority = todoData.priority)
                            }
                        }

                        // Expand/Collapse button on the right side
                        if (todoData.description.text.isNotBlank()) {
                            Spacer(modifier = Modifier.width(8.dp))
                            CompactExpandButton(
                                isExpanded = isExpanded,
                                onClick = { isExpanded = !isExpanded }
                            )
                        }
                    }

                    // Target time row
                    if (todoData.targetTime.text.isNotBlank()) {
                        Spacer(modifier = Modifier.height(TodoUIDimens.SpacerMedium))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(TodoUIDimens.SpacerSmall)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = todoData.targetTime.text,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Expanded description
                    if (todoData.description.text.isNotBlank()) {
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(
                                expandFrom = Alignment.Top,
                                clip = false,
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 350,
                                    delayMillis = 100,
                                    easing = LinearEasing
                                )
                            ),
                            exit = shrinkVertically(
                                shrinkTowards = Alignment.Top,
                                clip = false,
                                animationSpec = tween(
                                    durationMillis = 250,
                                    easing = FastOutLinearInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        ) {
                            Column {
                                Spacer(modifier = Modifier.height(TodoUIDimens.SpacerLarge))
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                ) {
                                    Text(
                                        text = todoData.description.text,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(
    category: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = color.copy(alpha = 0.12f)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun PriorityBadge(
    priority: Int,
    modifier: Modifier = Modifier
) {
    val color = TodoColors.getPriorityColor(priority)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(5) { index ->
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(
                        if (index < priority) color else color.copy(alpha = 0.2f)
                    )
            )
        }
    }
}

@Composable
private fun CompactExpandButton(
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "chevron_rotation"
    )

    val scale by animateFloatAsState(
        targetValue = if (isExpanded) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "button_scale"
    )

    Box(
        modifier = modifier
            .size(40.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                    )
                )
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowDown,
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(24.dp)
                .rotate(rotation)
        )
    }
}

@MyPreview
@Composable
fun TodoListItemUIPreview() {
    AppPreviewContainer {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            TodoListItemUI(
                toDoTaskUIState = ToDoTaskUIState(
                    TodoUIModel(
                        "1",
                        TextFieldValue("Buy groceries for the week"),
                        TextFieldValue("Milk, eggs, bread, fruits, vegetables, and coffee beans. Don't forget the organic items!"),
                        TextFieldValue("Shopping"),
                        priority = 4,
                        targetTime = TextFieldValue("Tomorrow 10:00 AM")
                    )
                )
            )
            TodoListItemUI(
                toDoTaskUIState = ToDoTaskUIState(
                    TodoUIModel(
                        "2",
                        TextFieldValue("Team standup meeting"),
                        TextFieldValue("Discuss Q1 roadmap and blockers"),
                        TextFieldValue("Work"),
                        priority = 5,
                        targetTime = TextFieldValue("Today 2:00 PM")
                    )
                )
            )
            TodoListItemUI(
                toDoTaskUIState = ToDoTaskUIState(
                    TodoUIModel(
                        "3",
                        TextFieldValue("Morning yoga session"),
                        TextFieldValue(""),
                        TextFieldValue("Health"),
                        priority = 2
                    )
                )
            )
        }
    }
}
