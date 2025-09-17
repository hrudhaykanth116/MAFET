package com.hrudhaykanth116.training.animations

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer

/**
 * https://developer.android.com/develop/ui/compose/graphics/draw/shadows
 */

fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
) = this.drawBehind {

    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint()
    paint.color = color

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

fun Modifier.doubleShadowDrop(
    shape: Shape,
    offset: Dp = 4.dp,
    blur: Dp = 8.dp,
) = this
    .dropShadow(shape, Color.Black.copy(0.25f), blur, offset, offset)
    .dropShadow(shape, Color.White.copy(0.25f), blur, -offset, -offset)

@Composable
fun ElevatedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    shape: Shape = RoundedCornerShape(80.dp),
    content: @Composable BoxScope.() -> Unit,
) {
    val interSource = remember { MutableInteractionSource() }
    val isPressed by interSource.collectIsPressedAsState()

    // Animate shadow offset and blur radius
    // To create a hide shadow animation on press
    val shadowOffset by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 4.dp
    )
    val shadowBlur by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 8.dp
    )

    Box(
        modifier = modifier
            // Apply shadow effect
            // .doubleShadowDrop(shape, shadowOffset, shadowBlur)
            .dropShadow(shape, Color.Black.copy(0.7f), shadowBlur, shadowOffset, shadowOffset)
            .background(Color(0xFF010203), shape)
            .clip(shape)
            .clickable(
                interactionSource = interSource,
                indication = LocalIndication.current,
                onClick = onClick
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@AppPreview
@Composable
private fun ElevatedButtonPreview() {
    AppPreviewContainer {
        ElevatedButton(
            modifier = Modifier
        ) {
            Text(text = "Hanvay", color = Color.Red, fontSize = 25.sp)
        }
    }
}