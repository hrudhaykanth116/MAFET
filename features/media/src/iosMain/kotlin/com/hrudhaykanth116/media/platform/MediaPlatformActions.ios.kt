package com.hrudhaykanth116.media.platform

import platform.Foundation.NSURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

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
}
