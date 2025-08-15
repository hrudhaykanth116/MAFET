package com.hrudhaykanth116.core.ui.components


import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.core.ui.models.ImageHolder
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ApiErrorScreen(
    apiError: NetworkDataSource.ApiError?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (apiError) {
        NetworkDataSource.ApiError.NoInternetError -> {
            // No internet ui
            ApiErrorScreenUI(
                title = stringResource(R.string.oops).toUIText(),
                description = stringResource(R.string.internet_offline_description).toUIText(),
                onRetry = onRetry,
                modifier = modifier,
                resId = R.drawable.icon_no_internet
            )
        }
        else -> {
            // Something went wrong ui
            ApiErrorScreenUI(
                title = stringResource(R.string.oops).toUIText(),
                description = stringResource(R.string.internet_offline_something_went_wrong_description).toUIText(),
                onRetry = onRetry,
                modifier = modifier,
                resId = R.drawable.ic_server_connection_lost
            )
        }
    }
}

@Composable
fun ApiErrorScreenUI(
    title: UIText,
    @DrawableRes resId: Int,
    modifier: Modifier = Modifier,
    description: UIText? = null,
    onRetry: () -> Unit,
) {
    // BackHandler {
    // }

    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier){
            AppImage(
                imageSource = ImageHolder.LocalDrawableResource(R.drawable.image_error_holder),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(60.dp)
            )
            AppIcon(
                resId = resId,
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
                fontSize = 12.ssp,
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )
        VerticalSpacer(6.sdp)
        if (description != null) {
            Text(
                text = description.getText(),
                style = TextStyle(
                    fontSize = 12.ssp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFD2D2D2),
                    textAlign = TextAlign.Center,
                )
            )
        }
        VerticalSpacer(20.sdp)
        AppFormButton(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = !isLoading,
            btnText = "RETRY".toUIText(),
            onClick = {
                onRetry()
                coroutineScope.launch() {
                    isLoading = true
                    delay(500)
                    isLoading = false
                }
            }
        )
    }
}

@Composable
fun ApiErrorScreenPreview() {
    AppPreviewContainer {
        ApiErrorScreenUI(
            onRetry = { },
            resId = R.drawable.icon_no_internet,
            title = stringResource(R.string.oops).toUIText(),
            description = stringResource(R.string.internet_offline_description).toUIText(),
            modifier = Modifier.fillMaxSize()
        )

    }
}