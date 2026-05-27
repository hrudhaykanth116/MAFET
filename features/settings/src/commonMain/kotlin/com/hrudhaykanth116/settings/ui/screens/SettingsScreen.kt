package com.hrudhaykanth116.settings.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.AppScreen
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.settings.domain.model.AppTheme
import com.hrudhaykanth116.settings.ui.models.SettingsUIEvent
import com.hrudhaykanth116.settings.ui.models.SettingsUIState

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    AppScreen(
        viewModel = viewModel
    ) { state ->
        SettingsContent(
            state = state,
            onEvent = viewModel::processEvent
        )
    }
}

@Composable
private fun SettingsContent(
    state: SettingsUIState,
    onEvent: (SettingsUIEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppToolbar(
            text = "Settings",
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            AppText(
                uiText = UIText.Text("Theme"),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            AppTheme.entries.forEach { theme ->
                ThemeOption(
                    theme = theme,
                    isSelected = state.theme == theme,
                    onClick = { onEvent(SettingsUIEvent.OnThemeChanged(theme)) }
                )
            }
        }
    }
}

@Composable
private fun ThemeOption(
    theme: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        AppText(
            uiText = UIText.Text(theme.name.lowercase().replaceFirstChar { it.uppercase() }),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
