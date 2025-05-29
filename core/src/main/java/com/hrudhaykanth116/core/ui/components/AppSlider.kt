package com.hrudhaykanth116.core.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSlider(
    value: Float,
    modifier: Modifier = Modifier,
    textToShow: String? = null,
    onValueChange: (Float) -> Unit,
    steps: Int = 0,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    thumbDisplay: (Float) -> String = { "" },
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


    // var value by remember {
    //     mutableFloatStateOf(finalValue)
    // }

    val animatedValue by animateFloatAsState(
        targetValue = value,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy
        ), label = "animatedValue"
    )

    Slider(
        value = animatedValue,
        valueRange = valueRange,
        steps = steps,
        modifier = modifier,
        interactionSource = interaction,
        onValueChange = {
            onValueChange(it)
        },
        onValueChangeFinished = {
            // onValueChange(animatedValue)
        },
        thumb = {


            if (!textToShow.isNullOrBlank()) {
                Text(
                    text = textToShow,
                    color = Color.Black,
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                        .padding(4.dp)
                )
            }else{
                Box(
                    Modifier
                        .size(48.dp)
                        .padding(4.dp)
                        .background(Color.White, CutCornerShape(20.dp))
                )
            }


        },
        // track = { sliderState ->
        //
        //     // Calculate fraction of the slider that is active
        //     val fraction by remember {
        //         derivedStateOf {
        //             (sliderState.value - sliderState.valueRange.start) / (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
        //         }
        //     }
        //
        //     Box(
        //         modifier = Modifier.fillMaxWidth()
        //             // .onGloballyPositioned {
        //             //     trackWidth = it.size.width
        //             // }
        //     ) {
        //         Box(
        //             Modifier
        //                 .fillMaxWidth(1f)
        //                 .align(Alignment.CenterStart)
        //                 .height(6.dp)
        //                 .padding(end = 16.dp)
        //                 .background(Color.Yellow, CircleShape)
        //         )
        //         // for (i in 0..steps) {
        //         //
        //         //     val stepX = (i * stepWidth) // Calculate X position for each step
        //         //
        //         //     Box(modifier = Modifier
        //         //         .size(2.dp, 7.dp)
        //         //         .offset(x = stepX)
        //         //         .background(Color.Gray)
        //         //     )
        //         //
        //         // }
        //
        //         // Row(
        //         //     modifier = Modifier.fillMaxWidth(),
        //         //     horizontalArrangement = Arrangement.SpaceBetween
        //         // ) {
        //         //     for (i in 0..< steps) {
        //         //         Box(
        //         //             modifier = Modifier.size(20.dp),
        //         //             contentAlignment = Alignment.Center
        //         //         ) {
        //         //             // Text(text = "⚫", fontSize = 12.sp)
        //         //             Box(
        //         //                 modifier = Modifier.size(width = 2.dp, height = 8.dp)
        //         //                     .background(color = Color.Gray)
        //         //             )
        //         //         }
        //         //     }
        //         // }
        //
        //         // Box(
        //         //     Modifier
        //         //         .fillMaxWidth(1f - fraction)
        //         //         .align(Alignment.CenterEnd)
        //         //         .height(6.dp)
        //         //         .padding(start = 16.dp)
        //         //         .background(Color.Yellow, CircleShape)
        //         // )
        //     }
        // }
    )

}

@Preview
@Composable
private fun AppSliderPreview() {

    AppSlider(
        5f,
        onValueChange = {},
        valueRange = 1f..5f,
        steps = 3,
        textToShow = "5"
    )

}