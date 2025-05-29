package com.hrudhaykanth116.core.common.ui.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.components.CenteredColumn

@Composable
fun AppPreviewContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    CenteredColumn(
        modifier = modifier.fillMaxSize(),
    ){
        content()
    }

}