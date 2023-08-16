package com.hrudhaykanth116.training.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hrudhaykanth116.core.common.utils.compose.MyPreview

@MyPreview
@Composable
fun Basic() {

    Box {
        Spacer(
            modifier = Modifier
                .matchParentSize() // Doesn't effect parent size.
                .fillMaxSize() // Effects parent size
                .background(color = Color.Cyan.copy(alpha = 0.4f))
        )
    }



}