package com.hrudhaykanth116.mafet.main

sealed interface MainUiState {
    object Loading : MainUiState
    object LoggedIn : MainUiState
    object LoggedOut: MainUiState
    object Training: MainUiState
}