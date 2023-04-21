package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.utils.ui.ToastHelper

@Composable
fun <T> AppUIState(
    state: UIState<T>,
    modifier: Modifier = Modifier,
    content: @Composable ((T) -> Unit)
) {
    Box(modifier.fillMaxSize()) {

        content(state.contentState)

        when (state) {
            is UIState.LoadingUIState -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is UIState.ErrorUIState -> {
                ToastHelper.showErrorToast(LocalContext.current)
            }
            is UIState.LoadedUIState<T> -> {

            }
        }
    }
}