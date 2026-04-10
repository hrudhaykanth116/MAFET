package com.hrudhaykanth116.composeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.hrudhaykanth116.composeapp.home.HomeScreen
import com.hrudhaykanth116.composeapp.models.AppScreenEffect
import com.hrudhaykanth116.composeapp.models.AppScreenEvent
import com.hrudhaykanth116.composeapp.models.AppScreenState
import com.hrudhaykanth116.composeapp.ui.components.AppGateDialog
import com.hrudhaykanth116.core.ui.components.AppScreen
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    appViewModel: AppViewModel = koinViewModel<AppViewModel>(),
) {
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        appViewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        appViewModel.effect.collect { effect ->
            when (effect) {
                is AppScreenEffect.OpenUrl -> {
                    if (effect.url.isNotBlank()) {
                        uriHandler.openUri(effect.url)
                    }
                }
            }
        }
    }

    AppScreen(appViewModel) { state: AppScreenState ->
        AppUI(
            appState = state,
            onGateAction = { action ->
                appViewModel.processEvent(AppScreenEvent.GateButtonAction(action))
            },
        )
    }
}

@Composable
fun AppUI(
    appState: AppScreenState,
    onGateAction: (action: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .screenBackground()
    ) {
        val gate = appState.activeGate
        if (gate != null) {
            AppGateDialog(
                gate = gate,
                onAction = onGateAction,
            )
        } else {
            HomeScreen(appState.features)
        }
    }
}

