package com.hrudhaykanth116.media.platform

expect class MediaPlatformActions {
    fun openUrl(url: String)
    fun shareContent(url: String, text: String)
}
