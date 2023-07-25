package com.hrudhaykanth116.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hrudhaykanth116.core.R

@Composable
fun AppImage(
    image: Any?,
    modifier: Modifier = Modifier,
    @StringRes contentDescriptionResId: Int = R.string.image_content_default_description,
    @DrawableRes placeHolder: Int = R.drawable.image_place_holder,
    @DrawableRes errorDrawable: Int = R.drawable.image_error_holder,
) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(contentDescriptionResId),
        modifier = modifier,
        placeholder = painterResource(placeHolder),
        contentScale = ContentScale.FillBounds,
        error = painterResource(id = errorDrawable),
    )
}