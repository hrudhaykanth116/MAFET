package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVKit.AVPlayerViewController
import platform.Foundation.NSURL

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun VideoPlayerScreen(
    videoUrl: String,
    modifier: Modifier
) {
    val player = remember(videoUrl) {
        NSURL(string = videoUrl)?.let { url ->
            AVPlayer(uRL = url)
        }
    }

    if (player == null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),
            contentAlignment = Alignment.Center
        ) {
            Text("Invalid video URL")
        }
        return
    }

    DisposableEffect(player) {
        player.play()
        onDispose {
            player.pause()
        }
    }

    UIKitView(
        factory = {
            val playerViewController = AVPlayerViewController()
            playerViewController.player = player
            playerViewController.showsPlaybackControls = true
            playerViewController.view
        },
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
    )
}
