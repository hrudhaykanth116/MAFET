package com.hrudhaykanth116.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun VideoPlayerScreen(
    videoUrl: String,
    modifier: Modifier = Modifier
)
