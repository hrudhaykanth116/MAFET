package com.hrudhaykanth116.core.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.decode.ImageSource
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.common.utils.compose.modifier.click.preventBelowTouch
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.common.utils.ui.ToastHelper
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.ImageHolder
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun <T> AppUIState(
    viewModel: UIStateViewModel<T, *, *>,
    modifier: Modifier = Modifier,
    // onUserMessageShown: (() -> Unit),
    // onRetry: (() -> Unit),
    content: @Composable ((T) -> Unit),
) {

    val state: UIState<T> by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    // Lets initialize data when this composable is first launched
    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    Box(modifier.fillMaxSize()) {

        when (state) {
            is UIState.Loading -> {
                state.contentState?.let { content(it) }
                if (state.contentState == null) {
                    CenteredColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFF040404))
                            .preventBelowTouch(),
                    ) {

                        // AppImage(
                        //     modifier = modifier.size(32.dp),
                        //     imageSource = ImageHolder.Gif(R.drawable.app_progress_gif)
                        // )

                        AppProgressBar(modifier = Modifier.size(70.sdp))

                        VerticalSpacer(height = 20.dp)
                        Text(
                            text = "Please Wait.",
                            style = TextStyle(
                                fontSize = 12.ssp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            )
                        )

                    }
                } else {
                    state.contentState?.let {
                        content(it)
                    }
                    CenteredColumn(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(color = Color.Transparent)
                            .preventBelowTouch(),
                    ) {

                        AppProgressBar(modifier = Modifier.size(80.sdp))
                        VerticalSpacer(height = 20.dp)
                        Text(
                            text = "Please Wait.",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            )
                        )

                    }
                }
            }

            is UIState.Error -> {
                state.contentState?.let { contentState ->
                    content(contentState)
                }

                // ApiErrorScreen(
                //     onRetry = {
                //         viewModel.onRetry()
                //     },
                //     apiError = NetworkDataSource.ApiError.CustomError("Something went wrong".toUIText()),
                //     modifier = Modifier
                //         .fillMaxSize()
                //         .background(color = Color(0xFF040404)),
                // )
            }

            is UIState.Idle -> {
                state.contentState?.let {
                    content(it)
                }

                when (val userMessage = state.userMessage) {
                    is UserMessage.Error -> showUserMessageAndResetState(context, userMessage.message.getText(context), viewModel)
                    is UserMessage.Success -> showUserMessageAndResetState(context, userMessage.message.getText(context), viewModel)
                    is UserMessage.Warning -> showUserMessageAndResetState(context, userMessage.message.getText(context), viewModel)
                    else -> {}
                }
            }
        }
    }
}

private fun <T> showUserMessageAndResetState(
    context: Context,
    message: String,
    viewModel: UIStateViewModel<T, *, *>,
) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    viewModel.onUserMessageShown()

    // coroutineScope.launch {
    //     delay(3.seconds)
    //     viewModel.onUserMessageShown()
    // }

    // CustomToast(
    //     pressOffset = IntOffset(0, 0),
    //     content = {
    //         // toastContent(state.userMessage, onUserMessageShown)
    //         AppToast(
    //             userMessage = it,
    //             onDismissRequest = onUserMessageShown
    //         )
    //     }
    // )
}