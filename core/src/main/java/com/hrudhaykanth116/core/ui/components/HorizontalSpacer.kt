package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.hrudhaykanth116.core.common.resources.Dimens

@Composable
fun HorizontalSpacer(width: Dp = Dimens.DEFAULT_PADDING) {
    Spacer(modifier = Modifier.width(width))
}