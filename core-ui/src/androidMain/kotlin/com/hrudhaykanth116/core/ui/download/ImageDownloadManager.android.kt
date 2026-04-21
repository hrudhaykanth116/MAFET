package com.hrudhaykanth116.core.ui.download

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

actual class ImageDownloadManager(private val context: Context) {

    actual suspend fun downloadFile(url: String, filename: String): Boolean =
        withContext(Dispatchers.IO) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    downloadApi29Plus(url, filename)
                else
                    downloadLegacy(url, filename)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    @android.annotation.TargetApi(Build.VERSION_CODES.Q)
    private fun downloadApi29Plus(url: String, filename: String): Boolean {
        val resolver = context.contentResolver
        var uri: android.net.Uri? = null
        return try {
            val mimeType = mimeTypeFor(filename)
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
            val collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            uri = resolver.insert(collection, values) ?: return false

            resolver.openOutputStream(uri)?.use { output ->
                val conn = openConnection(url)
                val code = (conn as? HttpURLConnection)?.responseCode
                if (code != null && code !in 200..299) {
                    resolver.delete(uri, null, null)
                    uri = null
                    return false
                }
                conn.getInputStream().use { it.copyTo(output) }
            }

            val update = ContentValues().apply { put(MediaStore.MediaColumns.IS_PENDING, 0) }
            resolver.update(uri, update, null, null)
            true
        } catch (e: Exception) {
            uri?.let { resolver.delete(it, null, null) }
            e.printStackTrace()
            false
        }
    }

    private fun downloadLegacy(url: String, filename: String): Boolean {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, filename)
        val conn = openConnection(url)
        val code = (conn as? HttpURLConnection)?.responseCode
        if (code != null && code !in 200..299) return false
        conn.getInputStream().use { input ->
            FileOutputStream(file).use { input.copyTo(it) }
        }
        return true
    }

    private fun openConnection(url: String) = URL(url).openConnection().apply {
        connectTimeout = 30_000
        readTimeout = 60_000
        setRequestProperty("User-Agent", "Mozilla/5.0")
        connect()
    }

    private fun mimeTypeFor(filename: String) = when {
        filename.endsWith(".mp4", ignoreCase = true) -> "video/mp4"
        filename.endsWith(".png", ignoreCase = true) -> "image/png"
        else -> "image/jpeg"
    }
}
