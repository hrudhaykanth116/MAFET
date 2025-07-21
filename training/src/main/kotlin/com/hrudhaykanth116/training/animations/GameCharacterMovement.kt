package com.hrudhaykanth116.training.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toOffset
import kotlin.math.roundToInt

@Composable
fun GameCharacterMovement() {
    // val endPosition = Offset(250f, 400f)

    val controlPointActualLocation = Offset(300f, -250f)
    // val controlPoint = remember { mutableStateOf(controlPointActualLocation) }

    var x: Float by remember { mutableFloatStateOf(100f) }
    var y: Float by remember { mutableFloatStateOf(100f) }

    val position = remember {
        Animatable(
            Offset(100f, 100f),
            Offset.VectorConverter
        )
    }

    // LaunchedEffect(x, y) {
    //     position.animateTo(
    //         targetValue = IntOffset(x.roundToInt(), y.roundToInt()).toOffset(),
    //         // animationSpec = keyframes {
    //         //     durationMillis = 5000
    //         //     controlPoint.value at 2500 // midway point controlled by the draggable control point
    //         // }
    //     )
    // }


    val config = LocalConfiguration.current
    val density = LocalDensity.current

    val iconSize = 50.dp
    val iconSizePx = with(density) { iconSize.toPx() } // Assuming your Icon is 50.dp

    val screenWidthPx = LocalWindowInfo.current.containerSize.width
    val screenHeightPx = LocalWindowInfo.current.containerSize.height

    // val screenWidthPx = with(density) { config.screenWidthDp.dp.toPx() }
    // val screenHeightPx = with(density) { config.screenHeightDp.dp.toPx() }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF8AE263))
    ) {

        val boxWithConstraintsScope = this

        Icon(
            Icons.Filled.Face,
            tint = Color(0xFFE2711A),
            contentDescription = "Localized description",
            modifier = Modifier
                .size(iconSize)
                // .offset{
                //     IntOffset(100, 100)
                // }
                .offset {
                    IntOffset(x.roundToInt(), y.roundToInt())
                }
        )

        DraggableControlPoint(
            onDragging = { xOffset: Float, yOffset: Float ->
                x = (x + xOffset).coerceIn(0f, screenWidthPx - iconSizePx)
                y = (y + yOffset).coerceIn(0f, screenHeightPx - iconSizePx)
            },
            onDragEnded = { xOffset: Float, yOffset: Float ->
                // x += xOffset
                // y += yOffset
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset {
                    controlPointActualLocation.round()
                }
        )
    }
}

@Composable
fun DraggableControlPoint(
    onDragging: (Float, Float) -> Unit,
    onDragEnded: (Float, Float) -> Unit,
    modifier: Modifier = Modifier,
) {

    var offsetX: Float by remember { mutableFloatStateOf(0f) }
    var offsetY: Float by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .absoluteOffset {
                IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
            }
            .size(30.dp)
            .background(Color.DarkGray, shape = CircleShape)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        offsetX = 0f
                        offsetY = 0f
                        onDragEnded(0f, 0f)
                    },
                    onDrag = { change, dragAmount ->
                        // val newX = (localPosition.x + dragAmount.x).coerceIn(0f, 600f)
                        // val newY = (localPosition.y + dragAmount.y).coerceIn(0f, 600f)
                        // localPosition = Offset(newX, newY)

                        change.consume()
                        offsetX = (offsetX + dragAmount.x).coerceIn(-200f, 200f)
                        offsetY = (offsetY + dragAmount.y).coerceIn(-200f, 200f)

                        onDragging(dragAmount.x, dragAmount.y)
                    }
                )
            }
    )
}

@Preview(name = "landscape", device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun GameCharacterMovementPreview() {
    GameCharacterMovement()
}