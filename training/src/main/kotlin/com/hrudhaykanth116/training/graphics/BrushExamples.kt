package com.hrudhaykanth116.training.graphics

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.training.R

// https://developer.android.com/jetpack/compose/graphics/draw/brush

val colorStops = arrayOf(
    0.0f to Color.Yellow,
    0.2f to Color.Red,
    1f to Color.Blue
)
val steppedBrush = Brush.horizontalGradient(colorStops = colorStops)

val horizontalBrush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue))
val linearBrush = Brush.linearGradient(listOf(Color.Red, Color.Blue))

// Image as a brush
// val imageBrush =
//     ShaderBrush(ImageShader(ImageBitmap.imageResource(id = R.drawable.sh_1)))

// val listColors = listOf(Color.Yellow, Color.Red, Color.Blue)
// val tileSize = with(LocalDensity.current) {
//     50.dp.toPx()
// }

// This one has better calculation by setting radius to bigger dimensions b/1 height and width
val largeRadialGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return RadialGradientShader(
            colors = listOf(Color(0xFF2be4dc), Color(0xFF243484)),
            center = size.center,
            radius = biggerDimension / 2f,
            colorStops = listOf(0f, 0.95f)
        )
    }
}


@Preview()
@Composable
fun BrushExamples() {


    GradientCircle()


}


@Composable
fun GradientCircle() {
    androidx.compose.foundation.Canvas(
        modifier = Modifier.size(200.dp),
        onDraw = {
            drawCircle(horizontalBrush)
        }
    )
}

