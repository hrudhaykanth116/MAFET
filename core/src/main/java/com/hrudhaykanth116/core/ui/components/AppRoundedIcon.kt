package com.hrudhaykanth116.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AppRoundedIcon(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.sdp,
    containerColor: Color = Color.Black.copy(alpha = 0.4f),
    tint: Color = Color.Unspecified,
    onClick: (() -> Unit)? = null,
) {

    Box(
        modifier
            .clip(CircleShape)
            .background(containerColor)
            // .graphicsLayer {
            //     alpha = 0.99f
            //     renderEffect = BlurEffect(20f, 20f, TileMode.Clamp)
            // }
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = tint,
            modifier = Modifier
                .size(iconSize)
                .padding(8.sdp)
        )
    }
}

@Preview
@Composable
private fun AppRoundedIconPreview() {
    AppRoundedIcon(
        icon = com.hrudhaykanth116.core.R.drawable.ic_back,
        tint = Color.Red,
        iconSize = 30.sdp,
        modifier = Modifier
    )
}