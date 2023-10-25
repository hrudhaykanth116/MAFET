package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.models.constants.widgets.ProgressBarType

@Composable
fun BoxScope.AppProgressBar(
    modifier: Modifier = Modifier,
    progressBarType: ProgressBarType = ProgressBarType.Circular(),
) {
    when (progressBarType) {
        is ProgressBarType.Circular -> {
            CircularProgressIndicator(
                modifier = modifier.align(Alignment.Center)
            )
        }
    }

}