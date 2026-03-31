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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.PlayCircle
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.composeapp.home.dashboard.models.DashboardScreenEvent
import com.hrudhaykanth116.composeapp.home.dashboard.models.DashboardScreenState
import com.hrudhaykanth116.composeapp.home.dashboard.models.TodoSummary
import com.hrudhaykanth116.composeapp.home.dashboard.models.TvSummary
import com.hrudhaykanth116.composeapp.home.dashboard.models.WeatherSummary
import com.hrudhaykanth116.core.ui.models.UIState
import org.koin.compose.viewmodel.koinViewModel

private val TodoGradient = listOf(Color(0xFF667eea), Color(0xFF764ba2))
private val WeatherGradient = listOf(Color(0xFF11998e), Color(0xFF38ef7d))
private val TvGradient = listOf(Color(0xFFeb3349), Color(0xFFf45c43))

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
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        TodoSummaryCard(summary = state.todoSummary)

        WeatherSummaryCard(summary = state.weatherSummary)

        TvSummaryCard(summary = state.tvSummary)

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun TodoSummaryCard(summary: TodoSummary?) {
    GradientCard(
        gradient = TodoGradient,
        icon = Icons.Filled.CheckCircle,
        title = "Top Priority Task"
    ) {
        if (summary != null) {
            Text(
                text = summary.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            PriorityBadge(priority = summary.priority)
        } else {
            EmptyStateText("No pending tasks")
        }
    }
}

@Composable
private fun WeatherSummaryCard(summary: WeatherSummary?) {
    GradientCard(
        gradient = WeatherGradient,
        icon = null,
        title = "Current Weather"
    ) {
        if (summary != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = summary.temperature.getText(),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = summary.condition.getText(),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f)
            )
            summary.location?.let {
                Spacer(modifier = Modifier.height(8.dp))
                LocationChip(location = it)
            }
        } else {
            EmptyStateText("Weather unavailable")
        }
    }
}

@Composable
private fun TvSummaryCard(summary: TvSummary?) {
    GradientCard(
        gradient = TvGradient,
        icon = Icons.Outlined.PlayCircle,
        title = "Currently Watching"
    ) {
        if (summary != null) {
            Text(
                text = summary.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RatingStars(rating = summary.rating)
                summary.lastEpisode?.let {
                    EpisodeBadge(episode = it)
                }
            }
        } else {
            EmptyStateText("No shows being watched")
        }
    }
}

@Composable
private fun GradientCard(
    gradient: List<Color>,
    icon: ImageVector?,
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(gradient))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge.copy(
                            letterSpacing = 1.sp
                        ),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                content()
            }
        }
    }
}

@Composable
private fun PriorityBadge(priority: Int) {
    val (backgroundColor, text) = when {
        priority >= 8 -> Color(0xFFFF6B6B) to "HIGH"
        priority >= 5 -> Color(0xFFFFE066) to "MEDIUM"
        else -> Color(0xFF69DB7C) to "LOW"
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor.copy(alpha = 0.9f)
    ) {
        Text(
            text = "Priority: $text",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun LocationChip(location: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📍",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = location,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun RatingStars(rating: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            val filled = index < (rating / 2)
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = if (filled) Color(0xFFFFD700) else Color.White.copy(alpha = 0.3f),
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$rating/10",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.White
        )
    }
}

@Composable
private fun EpisodeBadge(episode: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.White.copy(alpha = 0.2f)
    ) {
        Text(
            text = episode,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.White
        )
    }
}

@Composable
private fun EmptyStateText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White.copy(alpha = 0.7f)
    )
}
