package com.hrudhaykanth116.media.platform

expect class MediaPlatformActions {
    fun openUrl(url: String)
    fun shareContent(url: String, text: String)
    suspend fun downloadFile(url: String, filename: String): Boolean
}
