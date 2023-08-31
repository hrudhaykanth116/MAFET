package com.hrudhaykanth116.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.models.UIState2

@Composable
fun <T> AppUIState2(
    state: UIState2,
    modifier: Modifier = Modifier,
    onUserMessageShown: (() -> Unit),
    content: @Composable ((T?) -> Unit),
) {
    /*Logger.d("compose", "AppUIState2: ${state}")

    state.userMessage?.let {
        ToastHelper.show(LocalContext.current, it)
        onUserMessageShown
    }

    Box(modifier.fillMaxSize()) {

        when (state) {
            is UIState2.Loading -> {
                state.contentState?.let { content(it) }
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UIState2.Error -> {
                state.contentState?.let {
                    content(it)
                    ToastHelper.showErrorToast(LocalContext.current, state.uiText)
                } ?: AppText(
                    uiText = state.uiText ?: "".toUIText(),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UIState2.Idle -> {
                content(state.contentState)
            }
        }
    }*/
}