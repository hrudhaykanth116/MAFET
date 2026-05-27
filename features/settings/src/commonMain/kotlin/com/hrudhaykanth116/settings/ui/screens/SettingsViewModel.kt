package com.hrudhaykanth116.settings.ui.screens

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.settings.domain.use_cases.GetThemeUseCase
import com.hrudhaykanth116.settings.domain.use_cases.SetThemeUseCase
import com.hrudhaykanth116.settings.ui.models.SettingsUIEvent
import com.hrudhaykanth116.settings.ui.models.SettingsUIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher,
) : UIStateViewModel<SettingsUIState, SettingsUIEvent, Unit>(
    initialState = UIState.Loading(SettingsUIState()),
    defaultState = SettingsUIState(),
    networkMonitor = networkMonitor
) {

    override fun initializeData() {
        viewModelScope.launch(dispatcher) {
            getThemeUseCase().collectLatest { theme ->
                setIdleState { copy(theme = theme) }
            }
        }
    }

    override fun processEvent(event: SettingsUIEvent) {
        when (event) {
            is SettingsUIEvent.OnThemeChanged -> {
                viewModelScope.launch(dispatcher) {
                    setThemeUseCase(event.theme)
                }
            }
        }
    }
}
