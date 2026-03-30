package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.modifier.click.preventBelowTouch


@Composable
fun AppProgressBar(
    modifier: Modifier = Modifier,
    message: UIText? = null,
) {
    CenteredColumn(
        modifier = modifier
            .preventBelowTouch(),
    ) {

        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = Color(0xFFFFFFFF),
            strokeWidth = 4.dp
        )

        // val composition by rememberLottieComposition(LottieCompositionSpec.Asset("hour_glass_loading_big.json"))
        //
        // val progress by animateLottieCompositionAsState(
        //     composition,
        //     iterations = LottieConstants.IterateForever,
        //     isPlaying = true,
        //     restartOnPlay = true,
        //     cancellationBehavior = LottieCancellationBehavior.Immediately
        // )
        //
        // LottieAnimation(
        //     composition = composition,
        //     progress = { progress },
        //     modifier = Modifier.size(100.dp)
        // )


        message?.let {
            VerticalSpacer(height = 20.dp)
            Text(
                text = it.getText(),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }


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