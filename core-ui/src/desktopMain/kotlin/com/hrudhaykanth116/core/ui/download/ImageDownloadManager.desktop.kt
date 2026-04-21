package com.hrudhaykanth116.core.ui.download

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

actual class ImageDownloadManager {

    actual suspend fun downloadFile(url: String, filename: String): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val dir = File(System.getProperty("user.home"), "Downloads")
                if (!dir.exists()) dir.mkdirs()
                val file = File(dir, filename)
                val conn = URL(url).openConnection().apply {
                    connectTimeout = 30_000
                    readTimeout = 60_000
                    setRequestProperty("User-Agent", "Mozilla/5.0")
                    connect()
                }
                val code = (conn as? HttpURLConnection)?.responseCode
                if (code != null && code !in 200..299) return@withContext false
                conn.getInputStream().use { input ->
                    FileOutputStream(file).use { input.copyTo(it) }
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
}
