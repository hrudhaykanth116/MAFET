package com.hrudhaykanth116.training.animations

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer

// https://medium.com/@kappdev/inner-shadow-in-jetpack-compose-d80dcd56f6cf

fun Modifier.innerShadow(
    shape: Shape,
    color: Color = Color.Black,
    blur: Dp = 4.dp,
    offsetY: Dp = 2.dp,
    offsetX: Dp = 2.dp,
    spread: Dp = 0.dp
) = this.drawWithContent {

    drawContent()

    drawIntoCanvas { canvas ->

        val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
        val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

        val paint = Paint()
        paint.color = color

        canvas.saveLayer(size.toRect(), paint)
        canvas.drawOutline(shadowOutline, paint)

        paint.asFrameworkPaint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            if (blur.toPx() > 0) {
                maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
            }
        }

        paint.color = Color.Black

        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

val ShadowBlack = Color.Black.copy(0.56f)
val ShadowWhite = Color.White.copy(0.56f)
val Red = Color(0xFFE91E63)

@Composable
fun ConvexEffect(modifier: Modifier = Modifier) {
    Text(
        text = "Follow",
        color = Color.White,
        modifier = Modifier
            // Draw the background.
            .background(Red, CircleShape)
            // Shadow effect
            .innerShadow(
                shape = CircleShape, color = ShadowBlack,
                offsetY = (-2).dp, offsetX = (-2).dp
            )
            // Glare effect
            .innerShadow(
                shape = CircleShape, color = ShadowWhite,
                offsetY = 2.dp, offsetX = 2.dp
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
fun ConcaveEffect(modifier: Modifier = Modifier) {
    Text(
        text = "Hello World",
        color = Color.Black.copy(0.5f),
        modifier = Modifier
            // Glare effect
            .innerShadow(
                shape = CircleShape, color = ShadowWhite,
                offsetY = (-2).dp, offsetX = (-2).dp
            )
            // Shadow effect
            .innerShadow(
                shape = CircleShape, color = ShadowBlack,
                offsetY = 2.dp, offsetX = 2.dp
            )
            .padding(16.dp)
    )
}

@Preview
@Composable
private fun InnerShadowPreview() {
    AppPreviewContainer {
        ConvexEffect()
        VerticalSpacer()
        ConcaveEffect()
    }
}