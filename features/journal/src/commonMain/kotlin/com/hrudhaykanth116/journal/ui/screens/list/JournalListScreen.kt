package com.hrudhaykanth116.journal.ui.screens.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hrudhaykanth116.journal.ui.models.list.JournalListEffect
import com.hrudhaykanth116.journal.ui.models.list.JournalListUIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun JournalListScreen(
    viewModel: JournalListViewModel = koinViewModel(),
    onNavigateToEntry: (String?) -> Unit,
    onBackClicked: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is JournalListEffect.NavigateToEntry -> onNavigateToEntry(effect.entryId)
                JournalListEffect.NavigateToCreateEntry -> onNavigateToEntry(null)
            }
        }
    }

    val uiState by viewModel.uiStateFlow.collectAsState()

    JournalListScreenUI(
        uiState = uiState,
        onEvent = viewModel::processEvent,
        onCreateEntry = { onNavigateToEntry(null) },
        onBackClicked = onBackClicked
    )
}
