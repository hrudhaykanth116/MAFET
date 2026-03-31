package com.hrudhaykanth116.journal.ui.screens.entry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hrudhaykanth116.journal.ui.models.entry.JournalEntryEffect
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun JournalEntryScreen(
    entryId: String?,
    viewModel: JournalEntryViewModel = koinViewModel { parametersOf(entryId) },
    onBackClicked: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                JournalEntryEffect.NavigateBack -> onBackClicked()
            }
        }
    }

    val uiState by viewModel.uiStateFlow.collectAsState()

    JournalEntryScreenUI(
        uiState = uiState,
        onEvent = viewModel::processEvent,
        onBackClicked = onBackClicked
    )
}
