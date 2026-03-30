package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.todo.ui.TodoColors
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityField(
    value: Int,
    modifier: Modifier = Modifier,
    onPriorityChanged: (Int) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isDragging by interactionSource.collectIsDraggedAsState()

    val thumbScale by animateFloatAsState(
        targetValue = if (isDragging) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "thumbScale"
    )

    val animatedValue by animateFloatAsState(
        targetValue = value.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy
        ),
        label = "animatedValue"
    )

    val priorityColor = TodoColors.getPriorityColor(value)

    Slider(
        value = animatedValue,
        onValueChange = {
            onPriorityChanged(floor(it).toInt())
        },
        valueRange = 1f..5f,
        steps = 3,
        modifier = modifier,
        interactionSource = interactionSource,
        colors = SliderDefaults.colors(
            thumbColor = priorityColor,
            activeTrackColor = priorityColor.copy(alpha = 0.3f),
            inactiveTrackColor = Color(0xFFE5E7EB),
            activeTickColor = Color.Transparent,
            inactiveTickColor = Color.Transparent
        ),
        thumb = {
            Box(
                modifier = Modifier
                    .scale(thumbScale)
                    .size(24.dp)
                    .shadow(
                        elevation = if (isDragging) 6.dp else 2.dp,
                        shape = CircleShape,
                        ambientColor = priorityColor.copy(alpha = 0.2f),
                        spotColor = priorityColor.copy(alpha = 0.2f)
                    )
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = priorityColor,
                        shape = CircleShape
                    )
            )
        },
        track = { sliderState ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            ) {
                // Inactive track (full width)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color(0xFFE5E7EB))
                )

                // Active track (filled portion) with priority color
                val currentPriorityColor = TodoColors.getPriorityColor(value)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(
                            (sliderState.value - sliderState.valueRange.start) /
                                    (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                        )
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    currentPriorityColor.copy(alpha = 0.6f),
                                    currentPriorityColor
                                )
                            )
                        )
                )
            }
        }
    )
}

@Preview
@Composable
private fun PriorityFieldPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        PriorityField(
            value = 3,
            onPriorityChanged = {}
        )
    }
}