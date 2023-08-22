package com.hrudhaykanth116.core.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

@Composable
fun AppIcon(
    type: ImageHolder,
    modifier: Modifier = Modifier,
    contentDescriptionUIText: UIText? = null,
    tint: Color = LocalContentColor.current
) {

    when (type) {
        is ImageHolder.Bitmap -> {
            // TODO: Implement other image/icon resources
        }

        is ImageHolder.LocalDrawableResource -> {
            Icon(
                painter = painterResource(id = type.resId),
                contentDescription = contentDescriptionUIText?.getText(),
                tint = tint,
                modifier = modifier,
            )
        }

    }


}