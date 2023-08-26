package com.hrudhaykanth116.core.common.utils.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.gradientBackground(
    colors: List<Color>
): Modifier = composed {


    drawWithContent {
        drawRect(
            brush = Brush.verticalGradient(colors),
            size = size
        )
        drawContent()
    }


}