package com.hrudhaykanth116.media.ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.components.AppScreen
import com.hrudhaykanth116.media.domain.models.MediaType
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MediaDetailScreen(
    mediaId: Int,
    mediaType: MediaType,
    onBackClick: () -> Unit,
    onOpenUrl: (String) -> Unit,
    onShareMedia: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: MediaDetailViewModel = koinViewModel(
        key = "MediaDetail_${mediaId}_${mediaType}"
    ) {
        parametersOf(mediaId, mediaType)
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MediaDetailEffect.NavigateBack -> onBackClick()
                is MediaDetailEffect.ShareMedia -> onShareMedia(effect.url, effect.photographer)
                is MediaDetailEffect.OpenUrl -> onOpenUrl(effect.url)
            }
        }
    }

    AppScreen(viewModel = viewModel) { state ->
        state.mediaItem?.let { mediaItem ->
            MediaDetailScreenUI(
                mediaItem = mediaItem,
                onBackClick = { viewModel.processEvent(MediaDetailEvent.OnBackClick) },
                onShareClick = { viewModel.processEvent(MediaDetailEvent.OnShareClick) },
                onDownloadClick = { quality ->
                    viewModel.processEvent(MediaDetailEvent.OnDownloadClick(quality))
                },
                onPhotographerClick = { viewModel.processEvent(MediaDetailEvent.OnPhotographerClick) },
                modifier = modifier
            )
        }
    }
}
