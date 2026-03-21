package com.hrudhaykanth116.core.ui.components

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.request.ImageRequest.Builder
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.models.toImageHolder
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.empty
import mafet.core_ui.generated.resources.ic_close
import mafet.core_ui.generated.resources.ic_filter
import mafet.core_ui.generated.resources.ic_genie
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppImage(
    imageSource: ImageHolder,
    modifier: Modifier = Modifier,
    contentDescriptionRedId: StringResource = Res.string.empty,
    contentScale: ContentScale = ContentScale.FillBounds,
    placeHolder: Any? = null,
) {

    when (imageSource) {
        is ImageHolder.LocalDrawableResource -> {
            Image(
                painter = painterResource(imageSource.res),
                contentDescription = stringResource(contentDescriptionRedId),
                modifier = modifier,
                contentScale = contentScale
            )
        }

        is ImageHolder.Url -> {
            SubcomposeAsyncImage(
                model = Builder(LocalContext.current)
                    .data(imageSource.url)
                    // .crossfade(true)
                    .build(),
                loading = {
                    AppIcon(
                        resource = Res.drawable.ic_genie,
                        modifier = Modifier.requiredSize(40.dp),
                        tint = Color.White
                    )
                },
                error = {
                    AppIcon(
                        resource = Res.drawable.ic_close,
                        modifier = Modifier.requiredSize(40.dp),
                        tint = Color.White
                    )
                },

                contentDescription = stringResource(contentDescriptionRedId),
                contentScale = contentScale,
                modifier = modifier,
                // colorFilter = ColorFilter.tint(
                //     color = Color(0x4D000000),
                //     blendMode = BlendMode.Hue
                // ),
                // colorFilter = ColorFilter.colorMatrix(ColorMatrix())
                // alpha = 0.4f
            )
        }

        is ImageHolder.Gif -> {
            LoadGif(gifResId = imageSource.model, modifier, contentScale, placeHolder)
        }

        is ImageHolder.BitmapSource -> TODO()
        is ImageHolder.ImageBitmapSource -> TODO()
    }


}


@Composable
private fun LoadGif(
    gifResId: Any?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale,
    placeHolder: Any? = null,
) {

    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()


    AsyncImage(
        model = gifResId,
        contentDescription = "Local GIF image",
        imageLoader = imageLoader,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = placeHolder as? Painter? // Add more place holder customaization
    )
}

const val PLACE_HOLDER_LIGHT_GREY = 0xFF333333
const val PLACE_HOLDER_DARK_GREY = 0xFF222222
const val PLACE_HOLDER_BLACK = 0xFF111111

@Composable
fun RoundedImage(
    imageHolder: ImageHolder,
    modifier: Modifier = Modifier,
    spaceBetweenImageAndRound: Dp = 0.dp,
) {
    AppImage(
        imageSource = imageHolder,
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
        imageHolder = Res.drawable.ic_filter.toImageHolder(),
        modifier = Modifier
            .size(100.dp)
            .background(color = Color.Green),
        spaceBetweenImageAndRound = 10.dp
    )
}