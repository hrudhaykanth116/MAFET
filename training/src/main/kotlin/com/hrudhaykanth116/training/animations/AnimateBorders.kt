package com.hrudhaykanth116.training.animations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer

@Composable
fun AnimatedBorders(modifier: Modifier = Modifier) {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val colorBg = Color(0xFF5377E7)
    val colors =
        listOf(
            Color(0xFFFF595A),
            Color(0xFFFFC766),
            Color(0xFF35A07F),
            Color(0xFF35A07F),
            Color(0xFFFFC766),
            Color(0xFFFF595A)
        )

    val brush = Brush.sweepGradient(colors)

    // Canvas(
    //     modifier = modifier
    //         .fillMaxWidth()
    //         .height(200.dp)
    //         // .background(colorBg)
    // ) {
    //     // drawRoundRect(
    //     //     brush = brush,
    //     //     cornerRadius = CornerRadius(
    //     //         x = 20.dp.toPx(), y = 20.dp.toPx()
    //     //     )
    //     // )
    //
    //     rotate(degrees = angle) {
    //         drawCircle(
    //             brush = brush,
    //             radius = size.width,
    //             blendMode = BlendMode.SrcIn,
    //         )
    //     }
    //
    //     drawRoundRect(
    //         color = colorBg,
    //         topLeft = Offset(2.dp.toPx(), 2.dp.toPx()),
    //         size = Size(
    //             width = size.width - 4.dp.toPx(),
    //             height = size.height - 4.dp.toPx()
    //         ),
    //         cornerRadius = CornerRadius(
    //             x = 19.dp.toPx(),
    //             y = 19.dp.toPx()
    //         )
    //     )
    // }

    Surface(modifier = modifier, shape = RoundedCornerShape(20.dp)) {
        Surface(
            modifier =
            Modifier.clipToBounds().fillMaxWidth().padding(1.dp).drawWithContent {
                rotate(angle) {
                    drawCircle(
                        brush = brush,
                        radius = size.width,
                        blendMode = BlendMode.SrcIn,
                    )
                }
                drawContent()
            },
            color = Color.White,
            shape = RoundedCornerShape(19.dp)
        ) {
            Box(modifier = Modifier.padding(32.dp)) {

            }
        }
    }

}

@AppPreview
@Composable
private fun AnimatedBorderPreview() {
    AppPreviewContainer {
        AnimatedBorders(
            modifier = Modifier.padding(16.dp)
        )
    }
}