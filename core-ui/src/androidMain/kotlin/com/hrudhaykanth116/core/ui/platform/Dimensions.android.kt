package com.hrudhaykanth116.core.ui.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Android implementation using SDP/SSP libraries for scalable dimensions.
 */
actual val Int.sdp: Dp
    @Composable
    @ReadOnlyComposable
    get() {
        val context = LocalContext.current
        val resourceId = context.resources.getIdentifier("_${this}sdp", "dimen", context.packageName)
        return if (resourceId != 0) {
            val pixels = context.resources.getDimension(resourceId)
            Dp(pixels / context.resources.displayMetrics.density)
        } else {
            Dp(this.toFloat())
        }
    }

actual val Int.ssp: TextUnit
    @Composable
    @ReadOnlyComposable
    get() {
        val context = LocalContext.current
        val resourceId = context.resources.getIdentifier("_${this}ssp", "dimen", context.packageName)
        return if (resourceId != 0) {
            val pixels = context.resources.getDimension(resourceId)
            TextUnit(pixels / context.resources.displayMetrics.scaledDensity, androidx.compose.ui.unit.TextUnitType.Sp)
        } else {
            this.sp
        }
    }
