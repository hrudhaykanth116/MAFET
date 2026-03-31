package com.hrudhaykanth116.journal.ui.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.preview.MyPreview
import com.hrudhaykanth116.journal.resources.Res
import com.hrudhaykanth116.journal.resources.journal_list_empty_subtitle
import com.hrudhaykanth116.journal.resources.journal_list_empty_title
import com.hrudhaykanth116.journal.resources.journal_list_title
import com.hrudhaykanth116.journal.resources.journal_search_hint
import com.hrudhaykanth116.journal.ui.models.JournalEntryUIModel
import com.hrudhaykanth116.journal.ui.models.list.JournalListScreenEvent
import com.hrudhaykanth116.journal.ui.models.list.JournalListUIState
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

private val MoodColors = listOf(
    Color(0xFFE57373),
    Color(0xFFFFB74D),
    Color(0xFFFFD54F),
    Color(0xFF81C784),
    Color(0xFF4FC3F7)
)

private val CardBackground = Color.White.copy(alpha = 0.95f)
private val AccentColor = Color(0xFF6C63FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalListScreenUI(
    uiState: UIState<JournalListUIState>,
    onEvent: (JournalListScreenEvent) -> Unit,
    onCreateEntry: () -> Unit,
    onBackClicked: () -> Unit
) {
    val contentState = uiState.contentState ?: JournalListUIState()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    if (contentState.isSearchBarVisible) {
                        SearchBar(
                            searchText = contentState.search,
                            onSearchTextChanged = { onEvent(JournalListScreenEvent.OnSearchTextChanged(it)) }
                        )
                    } else {
                        Text(
                            text = stringResource(Res.string.journal_list_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color.White
                        )
                    }
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
                actions = {
                    IconButton(
                        onClick = {
                            if (contentState.isSearchBarVisible) {
                                onEvent(JournalListScreenEvent.OnCloseSearch)
                            } else {
                                onEvent(JournalListScreenEvent.OnSearchIconClicked)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (contentState.isSearchBarVisible) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = if (contentState.isSearchBarVisible) "Close search" else "Search",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateEntry,
                containerColor = Color.White,
                contentColor = AccentColor,
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 16.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create entry",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .screenBackground()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(
                visible = contentState.entries.isEmpty(),
                enter = fadeIn(tween(500)) + scaleIn(
                    spring(stiffness = Spring.StiffnessLow),
                    initialScale = 0.8f
                ),
                exit = fadeOut(tween(300)) + scaleOut()
            ) {
                EmptyState(modifier = Modifier.fillMaxSize())
            }

            AnimatedVisibility(
                visible = contentState.entries.isNotEmpty(),
                enter = fadeIn(tween(400)),
                exit = fadeOut(tween(300))
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    itemsIndexed(
                        items = contentState.entries,
                        key = { _, item -> item.id }
                    ) { index, entry ->
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInVertically(
                                initialOffsetY = { 100 },
                                animationSpec = tween(
                                    durationMillis = 400,
                                    delayMillis = index * 50
                                )
                            ) + fadeIn(
                                tween(
                                    durationMillis = 400,
                                    delayMillis = index * 50
                                )
                            )
                        ) {
                            JournalEntryCard(
                                entry = entry,
                                onClick = { onEvent(JournalListScreenEvent.OnEntryClicked(entry.id)) },
                                onDelete = { onEvent(JournalListScreenEvent.OnDeleteEntry(entry.id)) }
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = CardBackground,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color(0xFF6C757D),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF1A1A2E)
                ),
                cursorBrush = SolidColor(AccentColor),
                modifier = Modifier.weight(1f),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box {
                        if (searchText.isEmpty()) {
                            Text(
                                text = stringResource(Res.string.journal_search_hint),
                                style = TextStyle(
                                    fontSize = 16.sp,
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
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun JournalEntryCard(
    entry: JournalEntryUIModel,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val moodColor = MoodColors.getOrElse(entry.mood - 1) { MoodColors[2] }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = CardBackground,
        shadowElevation = 4.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
                    .background(moodColor)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(moodColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = entry.moodEmoji,
                                fontSize = 22.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = entry.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1A1A2E),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = entry.formattedDate,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF6C757D)
                            )
                        }
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFE57373),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = entry.bodyPreview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6C757D),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )

                if (entry.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        entry.tags.take(3).forEach { tag ->
                            TagChip(tag = tag)
                        }
                        if (entry.tags.size > 3) {
                            Text(
                                text = "+${entry.tags.size - 3}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF6C757D),
                                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TagChip(tag: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = AccentColor.copy(alpha = 0.1f)
    ) {
        Text(
            text = tag,
            style = MaterialTheme.typography.labelSmall,
            color = AccentColor,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "📖",
                    fontSize = 56.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(Res.string.journal_list_empty_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(Res.string.journal_list_empty_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Tap + to begin your journey",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

@MyPreview
@Composable
fun JournalListScreenUIPreview() {
    AppPreviewContainer {
        JournalListScreenUI(
            uiState = UIState.Idle(JournalListUIState()),
            onEvent = {},
            onCreateEntry = {},
            onBackClicked = {}
        )
    }
}

@MyPreview
@Composable
fun JournalListScreenUIWithEntriesPreview() {
    AppPreviewContainer {
        JournalListScreenUI(
            uiState = UIState.Idle(
                JournalListUIState(
                    entries = persistentListOf(
                        JournalEntryUIModel(
                            id = "1",
                            title = "Great day at work",
                            bodyPreview = "Today was really productive. I managed to finish all my tasks and even helped a colleague with their project...",
                            mood = 5,
                            moodEmoji = "😄",
                            tags = persistentListOf("work", "productivity", "success"),
                            formattedDate = "Today, 2:30 PM",
                            createdAt = 0
                        ),
                        JournalEntryUIModel(
                            id = "2",
                            title = "Feeling tired",
                            bodyPreview = "Didn't sleep well last night. Need to improve my sleep schedule and maybe cut down on screen time before bed...",
                            mood = 2,
                            moodEmoji = "😕",
                            tags = persistentListOf("health", "sleep"),
                            formattedDate = "Yesterday, 10:15 PM",
                            createdAt = 0
                        ),
                        JournalEntryUIModel(
                            id = "3",
                            title = "Weekend plans",
                            bodyPreview = "Planning to go hiking this weekend. The weather looks perfect and I've been wanting to explore the new trail...",
                            mood = 4,
                            moodEmoji = "😊",
                            tags = persistentListOf("outdoors"),
                            formattedDate = "Mar 28, 9:00 AM",
                            createdAt = 0
                        )
                    )
                )
            ),
            onEvent = {},
            onCreateEntry = {},
            onBackClicked = {}
        )
    }
}
