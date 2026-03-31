package com.hrudhaykanth116.media.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.components.AppScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MediaHomeScreen(
    mediaHomeViewModel: MediaHomeViewModel = koinViewModel(),
    onNavigateToDetail: (Int, com.hrudhaykanth116.media.domain.models.MediaType) -> Unit = { _, _ -> },
    onNavigateToSearch: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        mediaHomeViewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        mediaHomeViewModel.effect.collect { effect ->
            when (effect) {
                is MediaHomeEffect.NavigateToDetail -> {
                    onNavigateToDetail(effect.mediaId, effect.type)
                }
                is MediaHomeEffect.NavigateToSearch -> {
                    onNavigateToSearch()
                }
                is MediaHomeEffect.ShowFilters -> {
                    // Filter sheet is handled in UI
                }
            }
        }
    }

    AppScreen(viewModel = mediaHomeViewModel) { state ->
        MediaHomeScreenUI(
            uiState = state,
            onToggleMediaType = { type ->
                mediaHomeViewModel.processEvent(MediaHomeEvent.ToggleMediaType(type))
            },
            onFilterOrientation = { orientation ->
                mediaHomeViewModel.processEvent(MediaHomeEvent.FilterOrientation(orientation))
            },
            onApplyFilters = { filters ->
                mediaHomeViewModel.processEvent(MediaHomeEvent.ApplyFilters(filters))
            },
            onMediaItemClick = { item ->
                mediaHomeViewModel.processEvent(MediaHomeEvent.OnMediaItemClick(item))
            },
            onLoadMore = {
                mediaHomeViewModel.processEvent(MediaHomeEvent.LoadNextPage)
            },
            onOpenFilters = {
                mediaHomeViewModel.processEvent(MediaHomeEvent.OpenFilters)
            },
            onOpenSearch = {
                mediaHomeViewModel.processEvent(MediaHomeEvent.OpenSearch)
            },
            modifier = modifier
        )
    }
}
