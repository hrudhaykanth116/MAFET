package com.hrudhaykanth116.core.ui.download

expect class ImageDownloadManager {
    suspend fun downloadFile(url: String, filename: String): Boolean
}
