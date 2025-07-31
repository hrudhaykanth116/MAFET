package com.hrudhaykanth116.core.common.utils.compose

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=1080px,height=2340px,dpi=440,navigation=buttons",
    // device = "id:pixel_6_pro",
    // uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
annotation class MyPreview