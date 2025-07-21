package com.hrudhaykanth116.core.common.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hrudhaykanth116.core.ui.components.CenteredColumn

@Composable
fun AppPreviewContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    CenteredColumn(
        modifier = modifier.fillMaxSize().background(
            color = Color(0xFF0594F2)
        ),
    ){
        content()
    }

}