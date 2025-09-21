package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.modifier.click.preventBelowTouch
import ir.kaaveh.sdpcompose.ssp


@Composable
fun AppProgressBar(
    modifier: Modifier = Modifier,
) {
    CenteredColumn(
        modifier = modifier
            .preventBelowTouch(),
    ) {

        val composition by rememberLottieComposition(LottieCompositionSpec.Asset("hour_glass_loading_big.json"))

        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            restartOnPlay = true,
            cancellationBehavior = LottieCancellationBehavior.Immediately
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(100.dp)
        )

        VerticalSpacer(height = 20.dp)
        Text(
            text = "Please Wait ...",
            style = TextStyle(
                fontSize = 14.ssp,
                fontWeight = FontWeight(500),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )

    }
}

@AppPreview
@Composable
private fun AppProgressBarPreview() {
    AppPreviewContainer {
        CenteredColumn {
            AppProgressBar()
        }
    }
}