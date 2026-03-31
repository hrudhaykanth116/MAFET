package com.hrudhaykanth116.media.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.media.domain.models.MediaType
import com.hrudhaykanth116.media.platform.MediaPlatformActions
import com.hrudhaykanth116.media.ui.screens.detail.MediaDetailScreen
import com.hrudhaykanth116.media.ui.screens.home.MediaHomeScreen
import com.hrudhaykanth116.media.ui.screens.search.MediaSearchScreen
import org.koin.compose.koinInject

sealed class MediaNavigation {
    data object Home : MediaNavigation()
    data object Search : MediaNavigation()
    data class Detail(
        val mediaId: Int,
        val mediaType: MediaType,
        val returnTo: MediaNavigation
    ) : MediaNavigation()
}

@Composable
fun MediaScreen(
    modifier: Modifier = Modifier
) {
    val platformActions: MediaPlatformActions = koinInject()
    var currentScreen by remember { mutableStateOf<MediaNavigation>(MediaNavigation.Home) }

    when (val screen = currentScreen) {
        is MediaNavigation.Home -> {
            MediaHomeScreen(
                modifier = modifier,
                onNavigateToDetail = { mediaId, mediaType ->
                    currentScreen = MediaNavigation.Detail(
                        mediaId = mediaId,
                        mediaType = mediaType,
                        returnTo = MediaNavigation.Home
                    )
                },
                onNavigateToSearch = {
                    currentScreen = MediaNavigation.Search
                }
            )
        }

        is MediaNavigation.Search -> {
            MediaSearchScreen(
                modifier = modifier,
                onBackClick = {
                    currentScreen = MediaNavigation.Home
                },
                onNavigateToDetail = { mediaId, mediaType ->
                    currentScreen = MediaNavigation.Detail(
                        mediaId = mediaId,
                        mediaType = mediaType,
                        returnTo = MediaNavigation.Search
                    )
                }
            )
        }

        is MediaNavigation.Detail -> {
            MediaDetailScreen(
                mediaId = screen.mediaId,
                mediaType = screen.mediaType,
                modifier = modifier,
                onBackClick = {
                    currentScreen = screen.returnTo
                },
                onOpenUrl = { url ->
                    platformActions.openUrl(url)
                },
                onShareMedia = { url, photographer ->
                    platformActions.shareContent(url, photographer)
                }
            )
        }
    }
}