package com.hrudhaykanth116.core.ui.components

import android.R.id.message
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.common.mappers.mapToUIMessage
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import ir.kaaveh.sdpcompose.ssp

@Composable
fun <T> AppScreen(
    viewModel: UIStateViewModel<T, *, *>,
    content: @Composable ((T) -> Unit),
) {

    val uIState: UIState<T> by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    // Lets initialize data when this composable is first launched
    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }


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

    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {

        state.contentState?.let { contentState ->
            content(contentState)
        }

        when (state) {
            is UIState.Loading -> {
                AppProgressBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = if (state.contentState == null) Color(0xFF040404) else Color.Transparent)
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
                    is UserMessage.Error -> userMessage.message.getText(context)
                    is UserMessage.Success -> userMessage.message.getText(context)
                    is UserMessage.Warning -> userMessage.message.getText(context)
                    else -> null
                }?.let { message: String ->

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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