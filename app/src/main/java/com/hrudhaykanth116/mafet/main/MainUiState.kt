package com.hrudhaykanth116.mafet.main

sealed interface MainUiState {
    object Loading : MainUiState
    data class LoggedIn(val userName: String) : MainUiState
    object LoggedOut: MainUiState
}