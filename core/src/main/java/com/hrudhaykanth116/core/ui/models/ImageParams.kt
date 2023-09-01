package com.hrudhaykanth116.core.ui.models

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.hrudhaykanth116.core.R


data class ImageParams(
    val image: Any?,
    @StringRes val contentDescriptionResId: Int = R.string.image_content_default_description,
    val placeHolder: Painter = BrushPainter(
        Brush.radialGradient(
            colors = listOf(
                Color.Red,
                Color.Green
            )
        )
    ),
    // @DrawableRes val errorDrawable: Int = R.drawable.image_error_holder,
    val provideErrorDrawable: @Composable () -> Painter = {
        painterResource(id = R.drawable.ic_back)
    },
)