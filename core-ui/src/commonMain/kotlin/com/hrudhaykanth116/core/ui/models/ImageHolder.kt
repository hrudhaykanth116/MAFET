package com.hrudhaykanth116.core.ui.models

import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.compose.resources.DrawableResource

sealed class ImageHolder(val data: Any?){
    data class ImageBitmapSource(val imageBitmap: ImageBitmap?): ImageHolder(imageBitmap)
    data class LocalDrawableResource(val res: DrawableResource): ImageHolder(res)
    data class Url(val url: String?): ImageHolder(url)
    data class Gif(val model: Any?) : ImageHolder(model)
}

fun DrawableResource.toImageHolder(): ImageHolder.LocalDrawableResource {
    return ImageHolder.LocalDrawableResource(this)
}

fun String.toUrlImageHolder() = ImageHolder.Url(this)