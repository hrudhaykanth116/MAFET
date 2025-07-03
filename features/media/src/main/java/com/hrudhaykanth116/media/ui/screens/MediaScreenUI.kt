package com.hrudhaykanth116.media.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.media.ui.components.PhotoGridScreen

@Composable
fun MediaScreenUI(
    state: MediaScreenUIState,
    processEvent: (MediaScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    CenteredColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        PhotoGridScreen(
            state.photoList
        )
    }



}


@AppPreview
@Composable
private fun MediaScreenPreview() {

    AppPreviewContainer {
        MediaScreenUI(
            state = MediaScreenUIState(

            ),
            processEvent = {},
            modifier = Modifier,
        )
    }

}
