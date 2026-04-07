package com.hrudhaykanth116.composeapp.models

import com.hrudhaykanth116.composeapp.home.models.FeatureConfig

sealed interface MainUiState {
    object Loading : MainUiState
    data class LoggedIn(val features: List<FeatureConfig>) : MainUiState
    object LoggedOut : MainUiState
    data class Blocked(val config: AppGateConfig) : MainUiState
}