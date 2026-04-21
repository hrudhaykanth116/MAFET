package com.hrudhaykanth116.core.ui.download

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

actual fun getImageDownloadManager(): ImageDownloadManager {
    return object : KoinComponent {}.get<android.content.Context>().let { ImageDownloadManager(it) }
}
