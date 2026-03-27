package com.hrudhaykanth116.core.ui.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

actual val Int.sdp: Dp
    @Composable
    @ReadOnlyComposable
    get() = this.dp

actual val Int.ssp: TextUnit
    @Composable
    @ReadOnlyComposable
    get() = this.sp
