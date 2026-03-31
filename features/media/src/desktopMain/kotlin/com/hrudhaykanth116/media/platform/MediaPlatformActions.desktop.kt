package com.hrudhaykanth116.media.platform

actual class MediaPlatformActions {

    actual fun openUrl(url: String) {
        // TODO: Desktop implementation
    }

    actual fun shareContent(url: String, text: String) {
        // TODO: Desktop implementation
    }

    actual suspend fun downloadFile(url: String, filename: String): Boolean {
        // TODO: Desktop implementation
        return false
    }
}
