package com.hrudhaykanth116.core.ui.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap

sealed class ImageHolder(val data: Any?){
    data class ImageBitmapSource(val imageBitmap: ImageBitmap?): ImageHolder(imageBitmap)
    data class BitmapSource(val imageBitmap: android.graphics.Bitmap?): ImageHolder(imageBitmap)
    data class LocalDrawableResource(@DrawableRes val resId: Int): ImageHolder(resId)
    data class Url(val url: String?): ImageHolder(url)
}

fun Int.toImageHolder(): ImageHolder.LocalDrawableResource {
    return ImageHolder.LocalDrawableResource(this)
}

fun String.toUrlImageHolder() = ImageHolder.Url(this)