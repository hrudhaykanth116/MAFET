package com.hrudhaykanth116.core.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.hrudhaykanth116.core.data.ErrorState
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.platform.sdp
import com.hrudhaykanth116.core.ui.platform.ssp
import org.jetbrains.compose.resources.DrawableResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ApiErrorScreen(
    apiError: ErrorState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {

    CenteredColumn {
        Text("Unable to load weather data. Api failed to get data. This screen should not be seen.", style = MaterialTheme.typography.bodyMedium)
    }

    // when (apiError) {
    //     is ErrorState.NoNetwork -> {
    //         // No internet ui
    //         ApiErrorScreenUI(
    //             title = stringResource(R.string.oops).toUIText(),
    //             description = stringResource(R.string.internet_offline_description).toUIText(),
    //             onRetry = onRetry,
    //             modifier = modifier,
    //             resId = R.drawable.icon_no_internet
    //         )
    //     }
    //     is ErrorState.Api -> {
    //         // Api error ui
    //         ApiErrorScreenUI(
    //             title = apiError.message?.toUIText() ?: stringResource(R.string.something_went_wrong).toUIText(),
    //             description = apiError.description?.toUIText(),
    //             onRetry = onRetry,
    //             modifier = modifier,
    //             resId = R.drawable.ic_server_connection_lost
    //         )
    //     }
    //     else -> {
    //         // Something went wrong ui
    //         ApiErrorScreenUI(
    //             title = stringResource(R.string.oops).toUIText(),
    //             description = stringResource(R.string.internet_offline_something_went_wrong_description).toUIText(),
    //             onRetry = onRetry,
    //             modifier = modifier,
    //             resId = R.drawable.ic_server_connection_lost
    //         )
    //     }
    // }
}

@Composable
fun ApiErrorScreenUI(
    title: UIText,
    resource: DrawableResource,
    modifier: Modifier = Modifier,
    description: UIText? = null,
    onRetry: () -> Unit,
) {
    // BackHandler {
    // }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.sdp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier){
            AppImage(
                imageSource = ImageHolder.LocalDrawableResource(resource),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(100.sdp)
            )
            AppIcon(
                resource = resource,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.sdp)
                    .align(Alignment.Center)
            )
        }
        VerticalSpacer(20.sdp)
        Text(
            text = title.getText(),
            style = TextStyle(
                fontSize = 25.ssp,
                fontWeight = FontWeight(700),
                color = Color(0xFFEC4949),
                textAlign = TextAlign.Center,
            )
        )
        VerticalSpacer(6.sdp)
        if (description != null) {
            Text(
                text = description.getText(),
                style = TextStyle(
                    fontSize = 15.ssp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF232020),
                    textAlign = TextAlign.Center,
                )
            )
        }
        VerticalSpacer(20.sdp)
        AppFormButton(
            modifier = Modifier,
            enabled = true,
            btnText = "RETRY".toUIText(),
            onClick = {
                onRetry()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ApiErrorScreenPreview() {
    // AppPreviewContainer {
    //     ApiErrorScreenUI(
    //         onRetry = { },
    //         resId = R.drawable.icon_no_internet,
    //         title = stringResource(R.string.oops).toUIText(),
    //         description = stringResource(R.string.internet_offline_description).toUIText(),
    //         modifier = Modifier.fillMaxSize()
    //     )
    //
    // }
}