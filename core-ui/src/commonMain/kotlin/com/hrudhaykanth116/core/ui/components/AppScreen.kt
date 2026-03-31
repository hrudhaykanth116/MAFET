package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.collectAsState
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.UserMessage
import com.hrudhaykanth116.core.ui.platform.ssp

@Composable
fun <T> AppScreen(
    viewModel: UIStateViewModel<T, *, *>,
    content: @Composable ((T) -> Unit),
) {

    // TODO: kmp make this lifecycle aware like collectAsStateWithLifecycle
    val uIState: UIState<T> by viewModel.uiStateFlow.collectAsState()

    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    // During Navigation, ViewModel is preserved but Composable is recreated. So, initializeData will be called everytime composable is created.
    // LaunchedEffect(viewModel) {
    //     viewModel.initializeData()
    // }


    // state variable to preserve the exact type in when statement
    AppScreenUI(
        uIState,
        content,
        snackbarHostState = snackbarHostState,
        onUserMessageShown = { idleUIState: UIState.Idle<T> ->
            viewModel.onUserMessageShown(idleUIState)
        },
        onRetry = {
            viewModel.onRetry()
        }
    )

}

@Composable
fun <T> AppScreenUI(
    state: UIState<T>,
    content: @Composable ((T) -> Unit),
    snackbarHostState: SnackbarHostState,
    onUserMessageShown: (UIState.Idle<T>) -> Unit,
    onRetry: () -> Unit,
) {

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = data.visuals.actionLabel?.let {
                        when (it) {
                            UserMessage.ACTION_LABEL_SUCCESS -> Color(0xFF4CAF50)
                            UserMessage.ACTION_LABEL_ERROR -> Color(0xFFF44336)
                            UserMessage.ACTION_LABEL_WARNING -> Color(0xFFFF9800)
                            else -> Color(0xFF323232)
                        }
                    } ?: Color(0xFF323232)
                )
            }
        }
    ) { paddingValues ->

        Box(Modifier.fillMaxSize()) {

            state.contentState?.let { contentState ->
                content(contentState)
            }

            when (state) {
                is UIState.Loading -> {
                    AppProgressBar(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = if (state.contentState == null) Color(0xFF040404) else Color.Transparent),
                        state.message,
                    )
                }

                is UIState.Error -> {

                    ApiErrorScreen(
                        onRetry = onRetry,
                        domainError = state.errorState,
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                }

                is UIState.Idle -> {

                    val messageType = when (state.userMessage) {
                        is UserMessage.Error -> UserMessage.ACTION_LABEL_ERROR
                        is UserMessage.Success -> UserMessage.ACTION_LABEL_SUCCESS
                        is UserMessage.Warning -> UserMessage.ACTION_LABEL_WARNING
                        else -> null
                    }

                    val messageText = when (val userMessage = state.userMessage) {
                        is UserMessage.Error -> userMessage.message.getText()
                        is UserMessage.Success -> userMessage.message.getText()
                        is UserMessage.Warning -> userMessage.message.getText()
                        else -> null
                    }

                    if (messageText != null && messageType != null) {
                        LaunchedEffect(state.userMessage) {
                            snackbarHostState.showSnackbar(
                                message = messageText,
                                actionLabel = messageType,
                                duration = SnackbarDuration.Short
                            )
                            onUserMessageShown(state)
                        }
                    }
                }
            }

        }
    }
}

@AppPreview
@Composable
private fun AppScreenUIPreview() {

    val contentState: @Composable (String) -> Unit = { text ->
        CenteredColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                fontSize = 20.ssp,
                color = Color.Red
            )
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    AppPreviewContainer {
        CenteredColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Green)
        ) {
            AppScreenUI(
                state = UIState.Error(errorState = DomainError.NoNetwork, "Hello"),
                content = contentState,
                snackbarHostState = snackbarHostState,
                onUserMessageShown = {},
                onRetry = {}
            )
        }
    }

}