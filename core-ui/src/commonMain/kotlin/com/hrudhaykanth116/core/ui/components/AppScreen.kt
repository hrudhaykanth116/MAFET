package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.collectAsState
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.data.ErrorState
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

    // During Navigation, ViewModel is preserved but Composable is recreated. So, initializeData will be called everytime composable is created.
    // LaunchedEffect(viewModel) {
    //     viewModel.initializeData()
    // }


    // state variable to preserve the exact type in when statement
    AppScreenUI(
        uIState,
        content,
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
    onUserMessageShown: (UIState.Idle<T>) -> Unit,
    onRetry: () -> Unit,
) {

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
                    apiError = state.errorState,
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }

            is UIState.Idle -> {

                when (val userMessage = state.userMessage) {
                    is UserMessage.Error -> userMessage.message.getText()
                    is UserMessage.Success -> userMessage.message.getText()
                    is UserMessage.Warning -> userMessage.message.getText()
                    else -> null
                }?.let { message: String ->

                    // TODO: kmp use kmp way of showing toast or snackbar.
                    // ToastManager.showToast(message, ToastDuration.SHORT)
                    onUserMessageShown(state)

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

    AppPreviewContainer {
        CenteredColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Green)
        ) {
            AppScreenUI(
                state = UIState.Error(errorState = ErrorState.SomethingWentWrong, "Hello"),
                content = contentState,
                onUserMessageShown = {},
                onRetry = {}
            )
        }
    }

}