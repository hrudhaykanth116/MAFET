package com.hrudhaykanth116.tv.ui.screens.all

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hrudhaykanth116.tv.ui.preview.TvPreviewData

class TvHomeScreenPreviewParameterProvider : PreviewParameterProvider<TvHomeScreenUIState> {
    override val values: Sequence<TvHomeScreenUIState> = sequenceOf(
        TvHomeScreenUIState(
            categories = TvPreviewData.allCategories()
        )
    )
}
