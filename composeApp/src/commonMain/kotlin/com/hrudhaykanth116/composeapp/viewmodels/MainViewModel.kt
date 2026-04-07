package com.hrudhaykanth116.composeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.composeapp.RemoteConfigManager
import com.hrudhaykanth116.composeapp.models.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val remoteConfigManager: RemoteConfigManager,
) : ViewModel() {

    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.Loading)
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val config = remoteConfigManager.fetchConfig()
            val gate = config.appGate
            val isBlocked = gate.isEnabled && (
                gate.minVersionCode == 0L
                        // ||
                // BuildConfig.VERSION_CODE < gate.minVersionCode
            )

            if (isBlocked) {
                _state.update { MainUiState.Blocked(gate) }
                return@launch
            }
            _state.update { MainUiState.LoggedIn(config.features) }
        }
    }

    fun onLoggedIn() {
        // Re-use the already-fetched features from current state if available, else show all
        val currentFeatures = (_state.value as? MainUiState.LoggedIn)?.features
            ?: remoteConfigManager.getCachedFeatures()
        _state.update { MainUiState.LoggedIn(currentFeatures) }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}