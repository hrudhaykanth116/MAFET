package com.hrudhaykanth116.mafet.desktop

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.hrudhaykanth116.composeapp.App
import com.hrudhaykanth116.composeapp.home.models.FeatureConfig
import com.hrudhaykanth116.composeapp.models.Feature
import com.hrudhaykanth116.composeapp.models.MainUiState

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MAFET Desktop",
            state = rememberWindowState(width = 800.dp, height = 600.dp)
        ) {
            App(
                MainUiState.LoggedIn(
                    features = listOf(FeatureConfig(key = Feature.TODO.key, enabled = true))
                )
            )
        }
    }
}
