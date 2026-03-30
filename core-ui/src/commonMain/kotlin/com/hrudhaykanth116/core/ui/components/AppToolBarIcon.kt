package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hrudhaykanth116.core.ui.platform.sdp
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun AppToolBarIcon(
    iconResId: DrawableResource,
    onClick: () -> Unit,
    iconColor: Color = Color.White,
) {

    AppClickableIcon(
        resource = iconResId,
        onClick = onClick,
        iconColor = iconColor,
        modifier = Modifier.size(24.sdp)
    )

}