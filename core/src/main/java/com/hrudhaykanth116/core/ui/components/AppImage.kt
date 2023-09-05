package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.ui.models.ImageParams

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    imageParams: ImageParams,
) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageParams.image)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(imageParams.contentDescriptionResId),
        modifier = modifier,
        placeholder = imageParams.placeHolder,
        contentScale = ContentScale.FillBounds,
        error = imageParams.provideErrorDrawable(),
    )
}

@Composable
fun RoundedImage(
    imageParams: ImageParams,
    modifier: Modifier = Modifier,
    spaceBetweenImageAndRound: Dp = 0.dp,
) {
    AppImage(
        imageParams = imageParams,
        modifier = modifier,
        // modifier = Modifier
        //     .padding(spaceBetweenImageAndRound) // First padding will retain the size from incoming modifier.
        //     .clip(
        //         CircleShape
        //     )
        //     .then(
        //         modifier.border(2.dp, Color.Green, CircleShape)
        //             // .padding(spaceBetweenImageAndRound) // Will squeeze the image inside i.e padding
        //     )
    )
}

@Composable
@Preview
fun RoundImagePreview() {
    RoundedImage(
        imageParams = ImageParams(
            image = R.drawable.ic_filter,
        ),
        modifier = Modifier
            .size(100.dp)
            .background(color = Color.Green),
        spaceBetweenImageAndRound = 10.dp
    )
}