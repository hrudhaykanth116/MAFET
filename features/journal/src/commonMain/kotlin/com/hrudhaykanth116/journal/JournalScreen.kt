package com.hrudhaykanth116.journal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.components.AppScreen
import com.hrudhaykanth116.journal.ui.models.JournalScreenEvent
import com.hrudhaykanth116.journal.ui.screens.JournalScreenUI
import com.hrudhaykanth116.journal.ui.screens.JournalViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun JournalScreen(
    journalViewModel: JournalViewModel = koinViewModel(),
    onBackClicked: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        journalViewModel.initializeData()
    }

    AppScreen(viewModel = journalViewModel) { state ->
        JournalScreenUI(
            modifier = Modifier,
            uiState = state,
            onTextChanged = {
                journalViewModel.processEvent(JournalScreenEvent.TextChanged(it))
            },
            onSaveClicked = {
                journalViewModel.processEvent(JournalScreenEvent.SaveClicked)
            },
            onClearClicked = {
                journalViewModel.processEvent(JournalScreenEvent.ClearClicked)
            },
            onBackClicked = onBackClicked
        )
    }
}
