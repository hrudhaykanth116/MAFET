package com.hrudhaykanth116.core.common.utils.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

fun Modifier.screenBackground(): Modifier {
    return largeRadialBackground(
        listOf(
            Color(0xFF41C7CB),
            Color(0xFF1B7DC7),
        )
    )
}