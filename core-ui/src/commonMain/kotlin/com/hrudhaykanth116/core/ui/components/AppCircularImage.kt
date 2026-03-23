package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.preview.MyPreview
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_exclamation
import mafet.core_ui.generated.resources.ic_genie
import mafet.core_ui.generated.resources.image_content_default_description
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@MyPreview
@Composable
fun CircularImagePreview() {
    // CircularImage(
    //     modifier = Modifier.size(40.dp),
    //     image = R.drawable.profile_icon,
    //     placeHolder = R.drawable.profile_icon,
    //     contentDescriptionResId = R.string.image_content_default_description,
    // )
}

@Composable
fun AppCircularImage(
    image: ImageHolder?,
    modifier: Modifier = Modifier,
    placeHolder: DrawableResource = Res.drawable.ic_genie,
    errorHolder: DrawableResource = Res.drawable.ic_exclamation,
    contentDescriptionResId: StringResource = Res.string.image_content_default_description,
    onClicked: () -> Unit = {},
) {

    val context = LocalPlatformContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(image?.data)
            .crossfade(true)
            .build(),
        placeholder = painterResource(placeHolder),
        error = painterResource(errorHolder),
        contentDescription = stringResource(contentDescriptionResId),
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
            .clickable {
                onClicked()
            },
    )

    // hrudhay_check_list: Add more image types and make more concise. 
    // val imageModifier = Modifier
    //     .size(64.dp)
    //     .clickable { onClicked() }
    //     .clip(CircleShape)
    //     .border(2.dp, Color.Gray, CircleShape)
    //
    // when (imageHolder) {
    //     is ImageHolder.Bitmap -> {
    //         Image(
    //             bitmap = imageHolder.imageBitmap,
    //             contentDescription = contentDescription,
    //             contentScale = ContentScale.Crop,
    //             modifier = imageModifier
    //         )
    //     }
    //     is ImageHolder.ImageVector -> {
    //         Image(
    //             painter = painterResource(id = imageHolder.resId),
    //             contentDescription = contentDescription,
    //             contentScale = ContentScale.Crop,
    //             modifier = imageModifier
    //         )
    //     }
    // }


}