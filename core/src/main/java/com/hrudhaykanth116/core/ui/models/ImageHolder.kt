package com.hrudhaykanth116.core.ui.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
import coil.decode.ImageSource

sealed class ImageHolder(val data: Any?){
    data class ImageBitmapSource(val imageBitmap: ImageBitmap?): ImageHolder(imageBitmap)
    data class BitmapSource(val imageBitmap: android.graphics.Bitmap?): ImageHolder(imageBitmap)
    data class LocalDrawableResource(@DrawableRes val resId: Int): ImageHolder(resId)
    data class Url(val url: String?): ImageHolder(url)
    data class Gif(val model: Any?) : ImageHolder(model)
}

fun Int.toImageHolder(): ImageHolder.LocalDrawableResource {
    return ImageHolder.LocalDrawableResource(this)
}

fun String.toUrlImageHolder() = ImageHolder.Url(this)