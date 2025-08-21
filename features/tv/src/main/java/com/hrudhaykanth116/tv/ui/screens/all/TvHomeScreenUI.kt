package com.hrudhaykanth116.tv.ui.screens.all

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.openplaytech.core.ui.compose.composables.preview.AppPreview
import com.openplaytech.core.ui.compose.composables.preview.AppPreviewContainer

@Composable
fun TvHomeScreenUI(
    state: TvHomeScreenUIState,
    processEvent: (TvHomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

}


@AppPreview
@Composable
private fun TvHomeScreenPreview() {

    AppPreviewContainer {
        TvHomeScreenUI(
            state = TvHomeScreenUIState(),
            processEvent = {},
            modifier = Modifier,
        )
    }

}
