package com.hrudhaykanth116.media.platform

actual class MediaPlatformActions {

    actual fun openUrl(url: String) {
        // TODO: iOS implementation
    }

    actual fun shareContent(url: String, text: String) {
        // TODO: iOS implementation
    }

    actual suspend fun downloadFile(url: String, filename: String): Boolean {
        // TODO: iOS implementation
        return false
    }
}
