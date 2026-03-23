package com.hrudhaykanth116.core.ui.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/**
 * Scalable DP - Provides responsive sizing across different screen sizes.
 * On Android: Uses SDP library for scalable dimensions.
 * On Desktop: Uses regular DP with scaling factor.
 */
expect val Int.sdp: Dp
    @Composable @ReadOnlyComposable get

/**
 * Scalable SP - Provides responsive text sizing across different screen sizes.
 * On Android: Uses SSP library for scalable text.
 * On Desktop: Uses regular SP with scaling factor.
 */
expect val Int.ssp: TextUnit
    @Composable @ReadOnlyComposable get
