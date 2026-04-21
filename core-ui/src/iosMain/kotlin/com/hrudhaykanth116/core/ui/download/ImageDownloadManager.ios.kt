package com.hrudhaykanth116.core.ui.download

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.NSUserDomainMask
import platform.Foundation.downloadTaskWithURL
import kotlin.coroutines.resume

actual class ImageDownloadManager {

    actual suspend fun downloadFile(url: String, filename: String): Boolean {
        val nsUrl = NSURL.URLWithString(url) ?: return false
        val tempUrl = downloadToTemp(nsUrl) ?: return false
        return moveToDocuments(tempUrl, filename)
    }

    @OptIn(ExperimentalForeignApi::class)
    private suspend fun downloadToTemp(url: NSURL): NSURL? =
        suspendCancellableCoroutine { cont ->
            val task = NSURLSession.sharedSession.downloadTaskWithURL(url) { tempUrl, _, error ->
                cont.resume(if (error != null) null else tempUrl)
            }
            task.resume()
            cont.invokeOnCancellation { task.cancel() }
        }

    @OptIn(ExperimentalForeignApi::class)
    private fun moveToDocuments(tempUrl: NSURL, filename: String): Boolean {
        return try {
            val fm = NSFileManager.defaultManager
            val docsUrl = fm.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = true,
                error = null
            ) ?: return false
            val dest = docsUrl.URLByAppendingPathComponent(filename) ?: return false
            if (fm.fileExistsAtPath(dest.path ?: "")) fm.removeItemAtURL(dest, error = null)
            fm.moveItemAtURL(tempUrl, toURL = dest, error = null)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
