package com.hrudhaykanth116.media.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.core.common.ui.components.VideoPlayerScreen
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.components.AppProgressBar
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.media.ui.components.PhotoGridScreen

@Composable
fun MediaScreenUI(
    modifier: Modifier = Modifier,
    uiState: UIState<MediaScreenUIState>,
    state: MediaScreenUIState,
    processEvent: (MediaScreenEvent) -> Unit,
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CenteredColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            PhotoGridScreen(
                state.photoList
            )

            state.videoUrl?.let {
                VideoPlayerScreen(
                    it
                )
            }
        }

        if (uiState is UIState.Loading) {
            AppProgressBar(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = if (uiState.contentState == null) Color.White else Color.Transparent
                    )
            )
        }
    }

}


@AppPreview
@Composable
private fun MediaScreenPreview() {

    AppPreviewContainer {
        MediaScreenUI(
            modifier = Modifier,
            uiState = UIState.Idle(MediaScreenUIState()),
            state = MediaScreenUIState(),
            processEvent = {},
        )
    }

}
