package com.hrudhaykanth116.journal.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionSlider(
    modifier: Modifier = Modifier,
) {

    val interaction = remember { MutableInteractionSource() }
    val isDragging by interaction.collectIsDraggedAsState()
    val density = LocalDensity.current
    val offsetHeight by animateFloatAsState(
        targetValue = with(density) { if (isDragging) 36.dp.toPx() else 0.dp.toPx() },
        animationSpec = spring(
            stiffness = Spring.StiffnessMediumLow,
            dampingRatio = Spring.DampingRatioLowBouncy
        ), label = "offsetAnimation"
    )


    var value: Float by remember {
        mutableFloatStateOf(3f)
    }

    val animatedValue by animateFloatAsState(
        targetValue = value,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy
        ), label = "animatedValue"
    )

    Slider(
        value = animatedValue,
        valueRange = 1f..5f,
        steps = 3,
        modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
        interactionSource = interaction,
        onValueChange = {
            value = it
        },
        onValueChangeFinished = {
            // onValueChange(animatedValue)
        },
        thumb = {

            Box(
                Modifier
                    .size(48.dp)
                    .background(Color.White, CircleShape)
            ) {
                Text(
                    text = "mood",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-24 - 8).dp)
                )
            }


        },
    )

}

@Preview
@Composable
fun EmotionSliderPreview() {
    EmotionSlider(
        modifier = Modifier.background(color = Color.Green)
    )
}


