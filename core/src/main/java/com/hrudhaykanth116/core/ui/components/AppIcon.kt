package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.Coil
import coil.compose.AsyncImage
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.models.toImageHolder

@Composable
fun AppIcon(
    imageHolder: ImageHolder,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    uiText: UIText? = null,
    isTextFirst: Boolean = false,
    fontSize: TextUnit = TextUnit.Unspecified,
    contentDescriptionUIText: UIText? = null,
    tint: Color = Color.Unspecified,
) {



    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isTextFirst) {
            AppIconText(uiText, fontSize)
        }

        when (imageHolder) {
            is ImageHolder.Bitmap -> {
                // TODO: Implement other image/icon resources
            }

            is ImageHolder.LocalDrawableResource -> {
                Icon(
                    painter = painterResource(id = imageHolder.resId),
                    contentDescription = contentDescriptionUIText?.getText(),
                    tint = tint,
                    modifier = iconModifier
                )
            }

            is ImageHolder.Url -> {

            }
        }

        if (!isTextFirst) {
            AppIconText(uiText, fontSize)
        }

    }


}

@Composable
private fun AppIconText(
    uiText: UIText?,
    fontSize: TextUnit,
) {
    uiText?.let {
        VerticalSpacer(height = 2.dp)
        AppText(uiText = uiText, fontSize = fontSize)
    }
}

@MyPreview
@Composable
fun AppIconPreview() {
    AppIcon(
        imageHolder = R.drawable.profile_icon.toImageHolder(),
        uiText = "Profile image".toUIText(),
        modifier = Modifier
            .size(200.dp)
            .background(Color.Green),
        iconModifier = Modifier.size(100.dp),
        fontSize = 30.sp
    )
}