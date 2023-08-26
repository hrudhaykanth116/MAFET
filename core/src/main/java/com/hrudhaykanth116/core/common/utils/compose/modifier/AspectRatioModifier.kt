package com.hrudhaykanth116.core.common.utils.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.layout

fun Modifier.aspectRatio(ratio: Float): Modifier = composed {
    layout { measurable, constraints ->
        val width = constraints.maxWidth
        val height = (width / ratio).toInt()
        val placeable = measurable.measure(
            constraints.copy(minHeight = height, maxHeight = height)
        )
        layout(width, height) {
            placeable.place(0, 0)
        }
    }
}