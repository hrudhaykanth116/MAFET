package com.hrudhaykanth116.mafet.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.mafet.main.MainUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.Loading)
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(SPLASH_DELAY_MS)
            _state.update { MainUiState.LoggedIn }
        }
    }

    fun onLoggedIn() {
        _state.update { MainUiState.LoggedIn }
    }

    fun onLoggedOut() {
        _state.update { MainUiState.LoggedOut }
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val SPLASH_DELAY_MS = 1000L
    }
}