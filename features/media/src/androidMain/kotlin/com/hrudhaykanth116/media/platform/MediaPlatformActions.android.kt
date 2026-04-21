package com.hrudhaykanth116.media.platform

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * Android implementation of platform-specific actions for media handling.
 *
 * **Download Permissions:**
 * - Android 10+ (API 29+): No permissions needed, uses MediaStore API
 * - Android 9 and below (API < 29): Requires WRITE_EXTERNAL_STORAGE permission
 *   (declared in androidApp manifest with maxSdkVersion="28")
 *
 * Note: For Android 9 and below, the app should request WRITE_EXTERNAL_STORAGE
 * at runtime if not already granted. The download will fail silently if permission
 * is denied.
 */
actual class MediaPlatformActions(private val context: Context) {

    actual fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun shareContent(url: String, text: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "$text\n$url")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share via").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Downloads a file to the device's Downloads folder.
     *
     * Android 10+ (API 29+): Uses MediaStore API, no permissions required
     * Android 9 and below: Uses legacy file system, requires WRITE_EXTERNAL_STORAGE
     *
     * @return true if download successful, false otherwise
     */
    actual suspend fun downloadFile(url: String, filename: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    downloadFileApi29Plus(url, filename)
                } else {
                    downloadFileLegacy(url, filename)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    /**
     * Downloads file using MediaStore Downloads API (Android 10+).
     * No permissions required - Scoped Storage handles access automatically.
     * Uses MediaStore.Downloads collection which is correct for the Downloads directory.
     */
    @android.annotation.TargetApi(android.os.Build.VERSION_CODES.Q)
    private fun downloadFileApi29Plus(url: String, filename: String): Boolean {
        val resolver = context.contentResolver
        var uri: android.net.Uri? = null
        return try {
            val mimeType = when {
                filename.endsWith(".mp4", ignoreCase = true) -> "video/mp4"
                filename.endsWith(".jpg", ignoreCase = true) -> "image/jpeg"
                filename.endsWith(".jpeg", ignoreCase = true) -> "image/jpeg"
                filename.endsWith(".png", ignoreCase = true) -> "image/png"
                else -> "image/jpeg"
            }

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }

            val collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            uri = resolver.insert(collection, contentValues) ?: return false

            resolver.openOutputStream(uri)?.use { output ->
                val connection = URL(url).openConnection().apply {
                    connectTimeout = 30000
                    readTimeout = 60000
                    setRequestProperty("User-Agent", "Mozilla/5.0")
                }
                connection.connect()

                val responseCode = (connection as? java.net.HttpURLConnection)?.responseCode
                if (responseCode != null && responseCode !in 200..299) {
                    resolver.delete(uri, null, null)
                    uri = null
                    return false
                }

                connection.getInputStream().use { input ->
                    input.copyTo(output)
                }
            }

            val updateValues = ContentValues().apply { put(MediaStore.MediaColumns.IS_PENDING, 0) }
            resolver.update(uri, updateValues, null, null)
            true
        } catch (e: Exception) {
            uri?.let { resolver.delete(it, null, null) }
            e.printStackTrace()
            false
        }
    }

    /**
     * Downloads file using legacy file system (Android 9 and below).
     * Requires WRITE_EXTERNAL_STORAGE permission.
     */
    private fun downloadFileLegacy(url: String, filename: String): Boolean {
        return try {
            val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadDir.exists()) {
                downloadDir.mkdirs()
            }

            val file = File(downloadDir, filename)
            val connection = URL(url).openConnection().apply {
                connectTimeout = 30000
                readTimeout = 60000
                setRequestProperty("User-Agent", "Mozilla/5.0")
            }
            connection.connect()

            val responseCode = (connection as? java.net.HttpURLConnection)?.responseCode
            if (responseCode != null && responseCode !in 200..299) {
                return false
            }

            connection.getInputStream().use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }

            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).apply {
                data = Uri.fromFile(file)
            }
            context.sendBroadcast(intent)

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
