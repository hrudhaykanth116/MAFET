package com.hrudhaykanth116.composeapp.home.dashboard

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.composeapp.home.dashboard.models.DashboardScreenEvent
import com.hrudhaykanth116.composeapp.home.dashboard.models.DashboardScreenState
import com.hrudhaykanth116.composeapp.home.dashboard.models.JournalSummary
import com.hrudhaykanth116.composeapp.home.dashboard.models.TodoSummary
import com.hrudhaykanth116.composeapp.home.dashboard.models.TvSummary
import com.hrudhaykanth116.composeapp.home.dashboard.models.WeatherSummary
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.core.ui.preview.MyPreview
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import org.jetbrains.compose.resources.DrawableResource
import org.koin.compose.viewmodel.koinViewModel

private val TodoAccent = Color(0xFF6C5CE7)
private val WeatherAccent = Color(0xFFB604B3)
private val JournalAccent = Color(0xFFE84393)
private val TvAccent = Color(0xFFEB3349)

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    DashboardScreenUI(
        uiState = uiState,
        onEvent = viewModel::processEvent
    )
}

@Composable
private fun DashboardScreenUI(
    uiState: UIState<DashboardScreenState>,
    onEvent: (DashboardScreenEvent) -> Unit
) {
    when (uiState) {
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UIState.Idle, is UIState.Error -> {
            val state = uiState.contentState ?: DashboardScreenState()
            DashboardContent(state = state)
        }
    }
}

@Composable
private fun DashboardContent(state: DashboardScreenState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .screenBackground()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        DashboardGreeting()

        WeatherSection(summary = state.weatherSummary)

        TodoSection(summary = state.todoSummary)

        JournalSection(summary = state.journalSummary)

        TvSection(summary = state.tvSummary)

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun DashboardGreeting() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val greeting = when (now.hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..20 -> "Good evening"
        else -> "Good night"
    }
    val dayName = now.dayOfWeek.name.take(3).let { it.first() + it.drop(1).lowercase() }
    val monthName = now.month.name.take(3).let { it.first() + it.drop(1).lowercase() }
    val dateLabel = "$dayName, ${now.day} $monthName"

    Column {
        Text(
            text = greeting,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = dateLabel,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun WeatherSection(summary: WeatherSummary?) {
    FeatureSection(
        icon = Icons.Outlined.WbSunny,
        accent = WeatherAccent,
        title = "Weather"
    ) {
        SurfaceCard {
            if (summary != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = summary.temperature.getText(),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = summary.condition.getText(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        summary.location?.let {
                            Spacer(modifier = Modifier.height(10.dp))
                            AccentChip(
                                label = it,
                                accent = WeatherAccent,
                                leadingEmoji = "\uD83D\uDCCD",
                            )
                        }
                    }
                    summary.icon?.let {
                        WeatherIllustration(resource = it, accent = WeatherAccent)
                    }
                }
            } else {
                EmptyStateText("Weather unavailable")
            }
        }
    }
}

@Composable
private fun TodoSection(summary: TodoSummary?) {
    FeatureSection(
        icon = Icons.Outlined.CheckCircle,
        accent = TodoAccent,
        title = "Top Priority Task"
    ) {
        SurfaceCard {
            if (summary != null) {
                Text(
                    text = summary.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                PriorityBadge(priority = summary.priority)
            } else {
                EmptyStateText("No pending tasks — enjoy the calm")
            }
        }
    }
}

@Composable
private fun JournalSection(summary: JournalSummary?) {
    FeatureSection(
        icon = Icons.AutoMirrored.Outlined.MenuBook,
        accent = JournalAccent,
        title = "Today's Journal",
        trailing = summary?.let { { MoodBubble(emoji = it.moodEmoji, accent = JournalAccent) } }
    ) {
        SurfaceCard {
            if (summary != null) {
                Text(
                    text = summary.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (summary.bodyPreview.isNotBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = summary.bodyPreview,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 2,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                AccentChip(label = summary.dateLabel, accent = JournalAccent)
            } else {
                Text(
                    text = "No entry yet",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                EmptyStateText("Capture your day — it'll appear here.")
            }
        }
    }
}

@Composable
private fun TvSection(summary: TvSummary?) {
    FeatureSection(
        icon = Icons.Outlined.LiveTv,
        accent = TvAccent,
        title = "Currently Watching"
    ) {
        SurfaceCard {
            if (summary != null) {
                Text(
                    text = summary.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RatingStars(rating = summary.rating)
                    summary.lastEpisode?.let {
                        AccentChip(label = it, accent = TvAccent)
                    }
                }
            } else {
                EmptyStateText("No shows being watched")
            }
        }
    }
}

@Composable
private fun FeatureSection(
    icon: ImageVector,
    accent: Color,
    title: String,
    trailing: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FeatureIconBadge(icon = icon, accent = accent)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = accent
            )
            trailing?.invoke()
        }
        content()
    }
}

@Composable
private fun FeatureIconBadge(icon: ImageVector, accent: Color) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(accent),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )
    }
}

@Composable
private fun SurfaceCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun WeatherIllustration(resource: DrawableResource, accent: Color) {
    Box(
        modifier = Modifier
            .size(88.dp)
            .clip(CircleShape)
            .background(accent.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        AppIcon(
            resource = resource,
            iconModifier = Modifier.size(60.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun PriorityBadge(priority: Int) {
    val (accentColor, text) = when {
        priority >= 8 -> Color(0xFFE03E3E) to "HIGH"
        priority >= 5 -> Color(0xFFD99A00) to "MEDIUM"
        else -> Color(0xFF2E8B4C) to "LOW"
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = accentColor.copy(alpha = 0.12f)
    ) {
        Text(
            text = "Priority · $text",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = accentColor
        )
    }
}

@Composable
private fun AccentChip(
    label: String,
    accent: Color,
    leadingEmoji: String? = null,
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = accent.copy(alpha = 0.12f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingEmoji?.let {
                Text(text = it, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = accent
            )
        }
    }
}

@Composable
private fun MoodBubble(emoji: String, accent: Color) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(accent.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 22.sp
        )
    }
}

@Composable
private fun RatingStars(rating: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val filledCount = rating / 2
        repeat(5) { index ->
            val filled = index < filledCount
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = if (filled) Color(0xFFF5B301) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f),
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$rating/10",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun EmptyStateText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
}

@MyPreview
@Composable
private fun DashboardScreenUIPreview() {
    val sampleState = UIState.Idle(
        DashboardScreenState(
            todoSummary = TodoSummary(
                id = "1",
                title = "Finish quarterly report",
                priority = 9,
            ),
            weatherSummary = WeatherSummary(
                temperature = "22".toUIText(),
                condition = "Partly cloudy".toUIText(),
                location = "Hyderabad",
                icon = null,
            ),
            tvSummary = TvSummary(
                id = 1,
                name = "One Piece",
                rating = 9,
                lastEpisode = "S2E14",
            ),
            journalSummary = JournalSummary(
                id = "j1",
                title = "Morning reflections",
                bodyPreview = "Feeling calm and focused — pushed through some stuck problems and shipped the dashboard redesign.",
                moodEmoji = "\uD83D\uDE04",
                dateLabel = "Today",
            ),
        )
    )
    DashboardScreenUI(uiState = sampleState, onEvent = {})
}

@MyPreview
@Composable
private fun DashboardScreenUIPreviewEmpty() {
    val sampleState = UIState.Idle(DashboardScreenState())
    DashboardScreenUI(uiState = sampleState, onEvent = {})
}
