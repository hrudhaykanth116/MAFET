package com.hrudhaykanth116.core.common.utils.compose.modifier

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush

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

fun Modifier.largeRadialBackground(colorList: List<Color>): Modifier {
    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = colorList,
                center = size.center,
                radius = biggerDimension / 2f,
                colorStops = listOf(0f, 0.95f)
            )
        }
    }

    return background(largeRadialGradient)
}

