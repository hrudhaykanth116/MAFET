package com.hrudhaykanth116.todo.ui.screens.create

import AppDateTimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FlagCircle
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.compose.modifier.screenBackground
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.todo.R
import com.hrudhaykanth116.todo.ui.TodoColors
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState

@Composable
fun CreateOrUpdateTodoScreenUI(
    state: CreateOrUpdateTodoUIState,
    modifier: Modifier = Modifier,
    onTitleChanged: (TextFieldValue) -> Unit = {},
    onDescriptionChanged: (TextFieldValue) -> Unit = {},
    onCategoryChanged: (TextFieldValue) -> Unit = {},
    onCreateBtnClicked: () -> Unit = {},
    onPriorityChanged: (Int) -> Unit = {},
    onTargetTimeChanged: (Long) -> Unit = {},
    onTargetTimeDateTimePickerCloseRequest: () -> Unit = {},
    onTargetFieldClicked: () -> Unit = {},
    onCategoryFieldClicked: () -> Unit = {},
    onCategoryDismissRequest: () -> Unit = {},
    onCategorySelected: (com.hrudhaykanth116.todo.domain.model.TaskCategory) -> Unit = {},
    onBackClicked: () -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .screenBackground()
    ) {

        AppToolbar(
            text = stringResource(R.string.todo_create_title),
            onBackClicked = onBackClicked
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormSection(
                title = "Title",
                icon = Icons.Outlined.Title,
                isRequired = true
            ) {
                AppInputText(
                    textFieldData = TextFieldData(
                        hint = stringResource(R.string.todo_create_title_hint),
                        inputValue = state.todoUIModel.title,
                        error = state.titleError
                    ),
                    onInputChange = onTitleChanged,
                    singleLine = true
                )
            }

            FormSection(
                title = "Description",
                icon = Icons.Outlined.Description
            ) {
                AppInputText(
                    textFieldData = TextFieldData(
                        hint = stringResource(R.string.todo_create_description_hint),
                        inputValue = state.todoUIModel.description,
                        error = state.descriptionError
                    ),
                    onInputChange = onDescriptionChanged
                )
            }

            FormSection(
                title = "Schedule",
                icon = Icons.Outlined.CalendarToday
            ) {
                TargetTimeCard(
                    value = state.todoUIModel.targetTime,
                    placeholder = stringResource(R.string.todo_create_datetime_hint),
                    onClick = onTargetFieldClicked
                )
            }

            if (state.showTargetTimePicker) {
                AppDateTimePicker(
                    onDateTimeSelected = onTargetTimeChanged,
                    onDismissRequest = onTargetTimeDateTimePickerCloseRequest
                )
            }

            FormSection(
                title = "Category",
                icon = Icons.Outlined.Category
            ) {
                CategoryDropdownField(
                    selectedCategory = state.todoUIModel.category.text,
                    showDropdown = state.showCategoryDropdown,
                    onCategoryFieldClicked = onCategoryFieldClicked,
                    onCategorySelected = onCategorySelected,
                    onDismissRequest = onCategoryDismissRequest
                )
            }

            FormSection(
                title = "Priority Level",
                icon = Icons.Outlined.FlagCircle,
                modifier = Modifier.fillMaxWidth()
            ) {
                PriorityFieldWithLabels(
                    value = state.todoUIModel.priority,
                    onPriorityChanged = onPriorityChanged,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        AppFormButton(
            btnText = stringResource(R.string.todo_create_submit).toUIText(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
            onClick = onCreateBtnClicked
        )
    }
}

@Composable
private fun FormSection(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFAFAFB)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF3B82F6),
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
                if (isRequired) {
                    Text(
                        text = "*",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEF4444)
                    )
                }
            }

            content()
        }
    }
}

@Composable
private fun TargetTimeCard(
    value: TextFieldValue,
    placeholder: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Text(
            text = value.text.ifEmpty { placeholder },
            style = MaterialTheme.typography.bodyMedium,
            color = if (value.text.isEmpty()) {
                Color(0xFF9CA3AF)
            } else {
                Color(0xFF111827)
            }
        )
    }
}

@Composable
private fun CategoryDropdownField(
    selectedCategory: String,
    showDropdown: Boolean,
    onCategoryFieldClicked: () -> Unit,
    onCategorySelected: (com.hrudhaykanth116.todo.domain.model.TaskCategory) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onCategoryFieldClicked
                )
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (selectedCategory.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(TodoColors.getCategoryColor(selectedCategory))
                        )
                    }
                    Text(
                        text = selectedCategory.ifEmpty { "Select Category" },
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedCategory.isEmpty()) {
                            Color(0xFF9CA3AF)
                        } else {
                            Color(0xFF111827)
                        }
                    )
                }

                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = Color(0xFF6B7280),
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = onDismissRequest,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            com.hrudhaykanth116.todo.domain.model.TaskCategory.entries.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(TodoColors.getCategoryColor(category.key))
                            )
                            Text(
                                text = category.key,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    },
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Composable
private fun PriorityFieldWithLabels(
    value: Int,
    onPriorityChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(
                1 to "Very Low",
                2 to "Low",
                3 to "Medium",
                4 to "High",
                5 to "Very High"
            ).forEach { (priority, label) ->
                val priorityColor = TodoColors.getPriorityColor(priority)
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (priority == value) FontWeight.Bold else FontWeight.Normal,
                    color = if (priority == value) {
                        priorityColor
                    } else {
                        Color(0xFF9CA3AF)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            PriorityField(
                value = value,
                modifier = Modifier.fillMaxWidth(),
                onPriorityChanged = onPriorityChanged
            )
        }
    }
}

private fun getPriorityLabel(priority: Int): String {
    return when (priority) {
        1 -> "Very Low"
        2 -> "Low"
        3 -> "Medium"
        4 -> "High"
        5 -> "Very High"
        else -> "Medium"
    }
}

@MyPreview
@Composable
fun CreateOrUpdateTodoScreenUIPreview() {
    AppPreviewContainer {
        CreateOrUpdateTodoScreenUI(
            state = CreateOrUpdateTodoUIState(),
            onTitleChanged = {},
            onDescriptionChanged = {},
            onCategoryChanged = {},
            onCreateBtnClicked = {},
            onCategoryFieldClicked = {},
            onCategoryDismissRequest = {},
            onCategorySelected = {}
        )
    }
}