package com.hrudhaykanth116.training.shapes

import android.graphics.Matrix
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.PathParser
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer

// https://www.youtube.com/watch?v=7hxd7eOp54U

class FireShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Generic(
            path = PathParser.createPathFromPathData(
                "M10.67,0.73C11.165,0.279 11.934,0.285 12.429,0.736C14.181,2.335 15.826,4.057 17.362,5.921C18.061,5.032 18.854,4.063 19.712,3.273C20.213,2.816 20.988,2.816 21.489,3.279C23.686,5.316 25.546,8.007 26.854,10.563C28.143,13.081 29,15.655 29,17.47C29,25.346 22.664,32 14.778,32C6.804,32 0.556,25.34 0.556,17.464C0.556,15.093 1.686,12.198 3.439,9.334C5.21,6.427 7.712,3.396 10.67,0.73Z"
            ).asComposePath().apply {
                val pathSize = getBounds().size
                val matrix = Matrix()
                matrix.postScale(
                    size.width / pathSize.width,
                    size.height / pathSize.height
                )
                asAndroidPath().transform(matrix)
                val left = getBounds().left
                val top = getBounds().top
                translate(Offset(-left, -top))
            }
        )
    }
}

@Preview
@Composable
private fun FireShapePreview() {

    AppPreviewContainer {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(
                    FireShape()
                )
                .background(color = Color.Red),
            contentAlignment = Alignment.Center
        ) {

            Text(text = "Hanvay", fontSize = 14.sp, color = Color.Green)

        }
    }

}