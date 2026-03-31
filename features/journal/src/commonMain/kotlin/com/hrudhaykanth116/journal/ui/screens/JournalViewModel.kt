package com.hrudhaykanth116.journal.ui.screens

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.toErrorMessage
import com.hrudhaykanth116.core.ui.models.toSuccessMessage
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.journal.resources.Res
import com.hrudhaykanth116.journal.resources.journal_error_empty
import com.hrudhaykanth116.journal.resources.journal_success_saved
import com.hrudhaykanth116.journal.ui.models.JournalEffect
import com.hrudhaykanth116.journal.ui.models.JournalScreenEvent
import com.hrudhaykanth116.journal.ui.models.JournalUIState
import kotlinx.coroutines.CoroutineDispatcher

class JournalViewModel(
    private val networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher,
) : UIStateViewModel<JournalUIState, JournalScreenEvent, JournalEffect>(
    initialState = UIState.Idle(JournalUIState()),
    defaultState = JournalUIState(),
    networkMonitor = networkMonitor,
) {

    override fun initializeData() {
        // No initialization needed for simple journal screen
    }

    override fun processEvent(event: JournalScreenEvent) {
        when (event) {
            is JournalScreenEvent.TextChanged -> handleTextChanged(event.text)
            is JournalScreenEvent.SaveClicked -> handleSaveClicked()
            is JournalScreenEvent.ClearClicked -> handleClearClicked()
        }
    }

    private fun handleTextChanged(text: TextFieldValue) {
        setState {
            UIState.Idle(
                contentState?.copy(
                    journalText = text
                )
            )
        }
    }

    private fun handleSaveClicked() {
        val currentText = contentStateOrDefault.journalText.text

        if (currentText.isBlank()) {
            showUserMessage(Res.string.journal_error_empty.toUIText().toErrorMessage())
            return
        }

        setState {
            UIState.Idle(
                contentState?.copy(
                    isSaving = true
                )
            )
        }

        showUserMessage(Res.string.journal_success_saved.toUIText().toSuccessMessage())

        setState {
            UIState.Idle(
                contentState?.copy(
                    isSaving = false
                )
            )
        }
    }

    private fun handleClearClicked() {
        setState {
            UIState.Idle(
                contentState?.copy(
                    journalText = TextFieldValue()
                )
            )
        }
    }
}
