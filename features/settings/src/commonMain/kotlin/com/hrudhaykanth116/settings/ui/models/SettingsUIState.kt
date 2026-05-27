package com.hrudhaykanth116.settings.ui.models

import com.hrudhaykanth116.settings.domain.model.AppTheme

data class SettingsUIState(
    val theme: AppTheme = AppTheme.SYSTEM,
)

sealed interface SettingsUIEvent {
    data class OnThemeChanged(val theme: AppTheme) : SettingsUIEvent
}

sealed interface SettingsUIEffect
