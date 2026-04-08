package com.hrudhaykanth116.composeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.composeapp.home.HomeScreen
import com.hrudhaykanth116.composeapp.models.AppScreenEvent
import com.hrudhaykanth116.composeapp.models.AppScreenState
import com.hrudhaykanth116.composeapp.ui.components.AppEntryDialog
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    appViewModel: AppViewModel = koinViewModel<AppViewModel>(),
) {

    val uiState: AppScreenState by appViewModel.stateFlow.collectAsState()

    AppUI(
        uiState,
        onAppEntryDialogDismiss = { appViewModel.processEvent(AppScreenEvent.DismissDialog) },
        onAppEntryDialogAction = { action ->
            appViewModel.processEvent(AppScreenEvent.DialogButtonClicked(action))
        }
    )
}

@Composable
fun AppUI(
    appState: AppScreenState,
    onAppEntryDialogDismiss: () -> Unit,
    onAppEntryDialogAction: (action: String) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .screenBackground()
    ) {

        if (appState.dialogConfig != null) {
            val dialogConfig = appState.dialogConfig

            AppEntryDialog(
                config = dialogConfig,
                onDismiss = { onAppEntryDialogDismiss() },
                onButtonAction = { action ->
                    onAppEntryDialogAction(action)
                },
            )

        } else {
            HomeScreen(appState.features)
        }

    }
}
