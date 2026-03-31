package com.hrudhaykanth116.media.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSError
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.downloadTaskWithURL
import platform.Photos.PHAssetChangeRequest
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import kotlin.coroutines.resume

actual class MediaPlatformActions {

    actual fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication.openURL(nsUrl)
    }

    actual fun shareContent(url: String, text: String) {
        val items = listOfNotNull(
            text.ifEmpty { null },
            url.ifEmpty { null }?.let { NSURL.URLWithString(it) }
        )
        if (items.isEmpty()) return

        val activityController = UIActivityViewController(
            activityItems = items,
            applicationActivities = null
        )

        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.presentViewController(activityController, animated = true, completion = null)
    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun downloadFile(url: String, filename: String): Boolean {
        val nsUrl = NSURL.URLWithString(url) ?: return false

        val tempFileUrl = downloadToTempFile(nsUrl) ?: return false

        val hasPermission = requestPhotoLibraryPermission()
        if (!hasPermission) return false

        return saveToPhotoLibrary(tempFileUrl, filename)
    }

    @OptIn(ExperimentalForeignApi::class)
    private suspend fun downloadToTempFile(url: NSURL): NSURL? {
        return suspendCancellableCoroutine { cont ->
            val task = NSURLSession.sharedSession.downloadTaskWithURL(url) { tempUrl, _, error ->
                if (error != null || tempUrl == null) {
                    cont.resume(null)
                } else {
                    cont.resume(tempUrl)
                }
            }
            task.resume()
            cont.invokeOnCancellation { task.cancel() }
        }
    }

    private suspend fun requestPhotoLibraryPermission(): Boolean {
        return suspendCancellableCoroutine { cont ->
            PHPhotoLibrary.requestAuthorization { status ->
                cont.resume(status == PHAuthorizationStatusAuthorized)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private suspend fun saveToPhotoLibrary(fileUrl: NSURL, filename: String): Boolean {
        return suspendCancellableCoroutine { cont ->
            val isVideo = filename.endsWith(".mp4", ignoreCase = true) ||
                    filename.endsWith(".mov", ignoreCase = true)

            PHPhotoLibrary.sharedPhotoLibrary().performChanges({
                if (isVideo) {
                    PHAssetChangeRequest.creationRequestForAssetFromVideoAtFileURL(fileUrl)
                } else {
                    PHAssetChangeRequest.creationRequestForAssetFromImageAtFileURL(fileUrl)
                }
            }) { success, error ->
                cont.resume(success)
            }
        }
    }
}
