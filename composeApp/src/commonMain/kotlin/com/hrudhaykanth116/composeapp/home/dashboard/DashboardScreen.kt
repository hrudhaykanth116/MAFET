package com.hrudhaykanth116.composeapp.home.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.composeapp.home.dashboard.models.DashboardScreenEvent
import com.hrudhaykanth116.composeapp.home.dashboard.models.DashboardScreenState
import com.hrudhaykanth116.composeapp.home.dashboard.models.TodoSummary
import com.hrudhaykanth116.composeapp.home.dashboard.models.TvSummary
import com.hrudhaykanth116.composeapp.home.dashboard.models.WeatherSummary
import com.hrudhaykanth116.core.ui.models.UIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    LaunchedEffect(Unit){
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

        is UIState.Idle -> {
            val state = uiState.contentState ?: DashboardScreenState()
            DashboardContent(state = state)
        }

        is UIState.Error -> {
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
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        TodoSummaryCard(summary = state.todoSummary)

        WeatherSummaryCard(summary = state.weatherSummary)

        TvSummaryCard(summary = state.tvSummary)
    }
}

@Composable
private fun TodoSummaryCard(summary: TodoSummary?) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Top Priority Todo",
                style = MaterialTheme.typography.titleMedium
            )
            if (summary != null) {
                Text(
                    text = summary.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Priority: ${summary.priority}",
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                Text(
                    text = "No pending todos",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun WeatherSummaryCard(summary: WeatherSummary?) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Current Weather",
                style = MaterialTheme.typography.titleMedium
            )
            if (summary != null) {
                Text(
                    text = summary.temperature,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = summary.condition,
                    style = MaterialTheme.typography.bodyMedium
                )
                summary.location?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                Text(
                    text = "Weather unavailable",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun TvSummaryCard(summary: TvSummary?) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Top Rated Watching",
                style = MaterialTheme.typography.titleMedium
            )
            if (summary != null) {
                Text(
                    text = summary.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Rating: ${summary.rating}/10",
                    style = MaterialTheme.typography.bodyMedium
                )
                summary.lastEpisode?.let {
                    Text(
                        text = "Last: $it",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                Text(
                    text = "No shows being watched",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
