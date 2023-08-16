package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.common.utils.ui.ToastHelper

@Composable
fun <T> AppUIState(
    state: UIState<T>,
    modifier: Modifier = Modifier,
    onUserMessageShown: (() -> Unit),
    content: @Composable ((T?) -> Unit)
) {
    Logger.d("compose", "AppUIState: ${state}")

    state.userMessage?.let {
        ToastHelper.show(LocalContext.current, it)
        onUserMessageShown
    }

    Box(modifier.fillMaxSize()) {

        when (state) {
            is UIState.Loading -> {
                state.contentState?.let { content(it) }
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is UIState.Error -> {
                state.contentState?.let {
                    content(it)
                    ToastHelper.showErrorToast(LocalContext.current, state.uiText)
                } ?: AppText(
                    text = state.uiText ?: "".toUIText(),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is UIState.Loaded -> {
                content(state.contentState)
            }
        }
    }
}