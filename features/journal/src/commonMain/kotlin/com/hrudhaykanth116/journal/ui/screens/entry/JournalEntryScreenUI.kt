package com.hrudhaykanth116.journal.ui.screens.entry

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.core.ui.components.AppDatePicker
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.preview.MyPreview
import com.hrudhaykanth116.journal.resources.Res
import com.hrudhaykanth116.journal.resources.journal_entry_body_hint
import com.hrudhaykanth116.journal.resources.journal_entry_mood_label
import com.hrudhaykanth116.journal.resources.journal_entry_new_title
import com.hrudhaykanth116.journal.resources.journal_entry_tags_hint
import com.hrudhaykanth116.journal.resources.journal_entry_title_hint
import com.hrudhaykanth116.journal.resources.journal_save
import com.hrudhaykanth116.journal.resources.journal_update
import com.hrudhaykanth116.journal.ui.models.entry.JournalEntryScreenEvent
import com.hrudhaykanth116.journal.ui.models.entry.JournalEntryUIState
import org.jetbrains.compose.resources.stringResource

private val MoodColors = listOf(
    Color(0xFFE57373),
    Color(0xFFFFB74D),
    Color(0xFFFFD54F),
    Color(0xFF81C784),
    Color(0xFF4FC3F7)
)

private val MoodEmojis = listOf("😔", "😕", "😐", "😊", "😄")

private val CardBackground = Color.White.copy(alpha = 0.95f)
private val AccentColor = Color(0xFF6C63FF)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun JournalEntryScreenUI(
    uiState: UIState<JournalEntryUIState>,
    onEvent: (JournalEntryScreenEvent) -> Unit,
    onBackClicked: () -> Unit
) {
    val contentState = uiState.contentState ?: JournalEntryUIState()

    if (contentState.isDatePickerVisible) {
        AppDatePicker(
            onDateSelected = { onEvent(JournalEntryScreenEvent.OnDateSelected(it)) },
            onDismissRequest = { onEvent(JournalEntryScreenEvent.OnDatePickerDismissed) }
        )
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (contentState.isEditMode) "Edit Entry" else stringResource(Res.string.journal_entry_new_title),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .screenBackground()
                .padding(paddingValues)
        ) {
            if (contentState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    // Title Input with animation
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { -40 },
                            animationSpec = tween(400)
                        ) + fadeIn(tween(400))
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            color = CardBackground,
                            shadowElevation = 4.dp
                        ) {
                            BasicTextField(
                                value = contentState.title,
                                onValueChange = { onEvent(JournalEntryScreenEvent.OnTitleChanged(it)) },
                                textStyle = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1A1A2E)
                                ),
                                cursorBrush = SolidColor(AccentColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                enabled = !contentState.isSaving,
                                decorationBox = { innerTextField ->
                                    Box {
                                        if (contentState.title.text.isEmpty()) {
                                            Text(
                                                text = stringResource(Res.string.journal_entry_title_hint),
                                                style = TextStyle(
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color(0xFFADB5BD)
                                                )
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Date Selector
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { -40 },
                            animationSpec = tween(450, delayMillis = 50)
                        ) + fadeIn(tween(450, delayMillis = 50))
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !contentState.isSaving) {
                                    onEvent(JournalEntryScreenEvent.OnDateFieldClicked)
                                },
                            shape = RoundedCornerShape(16.dp),
                            color = CardBackground,
                            shadowElevation = 4.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Select date",
                                    tint = AccentColor,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = contentState.formattedDate.ifEmpty { "Select date" },
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (contentState.formattedDate.isEmpty())
                                            Color(0xFFADB5BD)
                                        else
                                            Color(0xFF1A1A2E)
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Body Input
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { -40 },
                            animationSpec = tween(500, delayMillis = 100)
                        ) + fadeIn(tween(500, delayMillis = 100))
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = CardBackground,
                            shadowElevation = 4.dp
                        ) {
                            BasicTextField(
                                value = contentState.body,
                                onValueChange = { onEvent(JournalEntryScreenEvent.OnBodyChanged(it)) },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color(0xFF1A1A2E),
                                    lineHeight = 24.sp
                                ),
                                cursorBrush = SolidColor(AccentColor),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                enabled = !contentState.isSaving,
                                decorationBox = { innerTextField ->
                                    Box {
                                        if (contentState.body.text.isEmpty()) {
                                            Text(
                                                text = stringResource(Res.string.journal_entry_body_hint),
                                                style = TextStyle(
                                                    fontSize = 16.sp,
                                                    color = Color(0xFFADB5BD),
                                                    lineHeight = 24.sp
                                                )
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Mood Section
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { -40 },
                            animationSpec = tween(550, delayMillis = 150)
                        ) + fadeIn(tween(550, delayMillis = 150))
                    ) {
                        Column {
                            Text(
                                text = stringResource(Res.string.journal_entry_mood_label),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            MoodSelector(
                                selectedMood = contentState.mood,
                                onMoodSelected = { onEvent(JournalEntryScreenEvent.OnMoodChanged(it)) },
                                enabled = !contentState.isSaving
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Tags Section
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { -40 },
                            animationSpec = tween(600, delayMillis = 200)
                        ) + fadeIn(tween(600, delayMillis = 200))
                    ) {
                        Column {
                            Text(
                                text = "Tags",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = CardBackground,
                                shadowElevation = 2.dp
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    BasicTextField(
                                        value = contentState.tagInput,
                                        onValueChange = { onEvent(JournalEntryScreenEvent.OnTagInputChanged(it)) },
                                        textStyle = TextStyle(
                                            fontSize = 15.sp,
                                            color = Color(0xFF1A1A2E)
                                        ),
                                        cursorBrush = SolidColor(AccentColor),
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 12.dp),
                                        enabled = !contentState.isSaving,
                                        singleLine = true,
                                        decorationBox = { innerTextField ->
                                            Box {
                                                if (contentState.tagInput.text.isEmpty()) {
                                                    Text(
                                                        text = stringResource(Res.string.journal_entry_tags_hint),
                                                        style = TextStyle(
                                                            fontSize = 15.sp,
                                                            color = Color(0xFFADB5BD)
                                                        )
                                                    )
                                                }
                                                innerTextField()
                                            }
                                        }
                                    )
                                    IconButton(
                                        onClick = { onEvent(JournalEntryScreenEvent.OnAddTag) },
                                        enabled = !contentState.isSaving && contentState.tagInput.text.isNotBlank()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add tag",
                                            tint = if (contentState.tagInput.text.isNotBlank())
                                                AccentColor
                                            else
                                                Color(0xFFADB5BD)
                                        )
                                    }
                                }
                            }

                            if (contentState.tags.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    contentState.tags.forEachIndexed { index, tag ->
                                        AnimatedVisibility(
                                            visible = true,
                                            enter = scaleIn(
                                                spring(
                                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                                    stiffness = Spring.StiffnessLow
                                                )
                                            ) + fadeIn()
                                        ) {
                                            InputChip(
                                                selected = false,
                                                onClick = { onEvent(JournalEntryScreenEvent.OnRemoveTag(tag)) },
                                                label = {
                                                    Text(
                                                        tag,
                                                        fontSize = 13.sp,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                },
                                                trailingIcon = {
                                                    Icon(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = "Remove tag",
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                },
                                                colors = InputChipDefaults.inputChipColors(
                                                    containerColor = Color.White.copy(alpha = 0.9f),
                                                    labelColor = AccentColor
                                                ),
                                                border = null
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Save Button
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { 40 },
                            animationSpec = tween(650, delayMillis = 250)
                        ) + fadeIn(tween(650, delayMillis = 250))
                    ) {
                        ElevatedButton(
                            onClick = { onEvent(JournalEntryScreenEvent.OnSaveClicked) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            enabled = !contentState.isSaving,
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = Color.White,
                                contentColor = AccentColor,
                                disabledContainerColor = Color.White.copy(alpha = 0.5f)
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 12.dp
                            )
                        ) {
                            if (contentState.isSaving) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = AccentColor,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = if (contentState.isEditMode)
                                        stringResource(Res.string.journal_update)
                                    else
                                        stringResource(Res.string.journal_save),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun MoodSelector(
    selectedMood: Int,
    onMoodSelected: (Int) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = CardBackground,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MoodEmojis.forEachIndexed { index, emoji ->
                val mood = index + 1
                val isSelected = selectedMood == mood
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.25f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = "scale"
                )
                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) MoodColors[index].copy(alpha = 0.25f) else Color.Transparent,
                    animationSpec = tween(300),
                    label = "backgroundColor"
                )
                val borderColor by animateColorAsState(
                    targetValue = if (isSelected) MoodColors[index] else Color.Transparent,
                    animationSpec = tween(300),
                    label = "borderColor"
                )

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .scale(scale)
                        .clip(CircleShape)
                        .background(backgroundColor)
                        .border(
                            width = if (isSelected) 3.dp else 0.dp,
                            color = borderColor,
                            shape = CircleShape
                        )
                        .clickable(
                            enabled = enabled,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onMoodSelected(mood) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = emoji,
                        fontSize = 28.sp
                    )
                }
            }
        }
    }
}

@MyPreview
@Composable
fun JournalEntryScreenUIPreview() {
    AppPreviewContainer {
        JournalEntryScreenUI(
            uiState = UIState.Idle(JournalEntryUIState()),
            onEvent = {},
            onBackClicked = {}
        )
    }
}

@MyPreview
@Composable
fun JournalEntryScreenUIEditModePreview() {
    AppPreviewContainer {
        JournalEntryScreenUI(
            uiState = UIState.Idle(
                JournalEntryUIState(
                    id = "1",
                    title = TextFieldValue("Great day at work"),
                    body = TextFieldValue("Today was really productive..."),
                    mood = 5,
                    isEditMode = true
                )
            ),
            onEvent = {},
            onBackClicked = {}
        )
    }
}
