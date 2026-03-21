package com.hrudhaykanth116.core.ui.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource


val placeHolderPainter = BrushPainter(
    Brush.radialGradient(
        colors = listOf(
            Color.Red,
            Color.Green
        )
    )
)

data class ImageParams(
    val image: Any?,
    // TODO: ktor ui
    // val contentDescriptionResId: Int = R.string.image_content_default_description,
    val placeHolder: Painter? = null,
    // val errorDrawable: Int = R.drawable.image_error_holder,
    // TODO: ktor ui
    // val provideErrorDrawable: @Composable () -> Painter = {
    //     painterResource(R.drawable.ic_back)
    // },
)