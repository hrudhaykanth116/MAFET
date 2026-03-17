package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AppToolBarIcon(
    iconResId: Int,
    onClick: () -> Unit,
    iconColor: Color = Color.White,
) {

    AppClickableIcon(
        resId = iconResId,
        onClick = onClick,
        iconColor = iconColor,
        modifier = Modifier.size(24.sdp)
    )

}