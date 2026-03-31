package com.hrudhaykanth116.journal.ui.screens.list

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.models.toSuccessMessage
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.journal.domain.use_cases.DeleteEntryUseCase
import com.hrudhaykanth116.journal.domain.use_cases.ObserveEntriesUseCase
import com.hrudhaykanth116.journal.resources.Res
import com.hrudhaykanth116.journal.resources.journal_success_deleted
import com.hrudhaykanth116.journal.ui.mappers.JournalUIMapper
import com.hrudhaykanth116.journal.ui.models.list.JournalListEffect
import com.hrudhaykanth116.journal.ui.models.list.JournalListScreenEvent
import com.hrudhaykanth116.journal.ui.models.list.JournalListUIState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class JournalListViewModel(
    private val observeEntriesUseCase: ObserveEntriesUseCase,
    private val deleteEntryUseCase: DeleteEntryUseCase,
    private val networkMonitor: NetworkMonitor,
    private val mapper: JournalUIMapper,
    private val dispatcher: CoroutineDispatcher
) : UIStateViewModel<JournalListUIState, JournalListScreenEvent, JournalListEffect>(
    initialState = UIState.Idle(JournalListUIState()),
    defaultState = JournalListUIState(),
    networkMonitor = networkMonitor
) {

    override fun initializeData() {
        observeEntries()
    }

    private fun observeEntries() {
        viewModelScope.launch(dispatcher) {
            contentStateFlow
                .map { it.search.takeIf { s -> s.isNotBlank() } }
                .distinctUntilChanged()
                .flatMapLatest { search ->
                    observeEntriesUseCase(search)
                }
                .collectLatest { entries ->
                    val uiModels = mapper.mapToUIModels(entries)
                    setState {
                        UIState.Idle(
                            contentState?.copy(
                                entries = uiModels.toImmutableList()
                            )
                        )
                    }
                }
        }
    }

    override fun processEvent(event: JournalListScreenEvent) {
        when (event) {
            is JournalListScreenEvent.OnSearchTextChanged -> onSearchTextChanged(event.text)
            JournalListScreenEvent.OnSearchIconClicked -> onSearchIconClicked()
            JournalListScreenEvent.OnCloseSearch -> onCloseSearch()
            is JournalListScreenEvent.OnEntryClicked -> onEntryClicked(event.entryId)
            is JournalListScreenEvent.OnDeleteEntry -> onDeleteEntry(event.entryId)
        }
    }

    private fun onSearchTextChanged(text: String) {
        setState {
            UIState.Idle(contentState?.copy(search = text))
        }
    }

    private fun onSearchIconClicked() {
        setState {
            UIState.Idle(contentState?.copy(isSearchBarVisible = true))
        }
    }

    private fun onCloseSearch() {
        setState {
            UIState.Idle(
                contentState?.copy(
                    isSearchBarVisible = false,
                    search = ""
                )
            )
        }
    }

    private fun onEntryClicked(entryId: String) {
        setEffect(JournalListEffect.NavigateToEntry(entryId))
    }

    private fun onDeleteEntry(entryId: String) {
        viewModelScope.launch(dispatcher) {
            deleteEntryUseCase(entryId)
            showUserMessage(UIText.StringRes(Res.string.journal_success_deleted).toSuccessMessage())
        }
    }
}
