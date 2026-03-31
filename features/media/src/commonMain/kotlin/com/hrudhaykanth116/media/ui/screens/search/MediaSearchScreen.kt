package com.hrudhaykanth116.media.ui.screens.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.components.AppScreen
import com.hrudhaykanth116.media.domain.models.MediaType
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MediaSearchScreen(
    onBackClick: () -> Unit,
    onNavigateToDetail: (Int, MediaType) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: MediaSearchViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MediaSearchEffect.NavigateBack -> onBackClick()
                is MediaSearchEffect.NavigateToDetail -> {
                    onNavigateToDetail(effect.mediaId, effect.type)
                }
            }
        }
    }

    AppScreen(viewModel = viewModel) { state ->
        MediaSearchScreenUI(
            uiState = state,
            onQueryChange = { query ->
                viewModel.processEvent(MediaSearchEvent.OnQueryChange(query))
            },
            onSearch = { query ->
                viewModel.processEvent(MediaSearchEvent.OnSearch(query))
            },
            onClearQuery = {
                viewModel.processEvent(MediaSearchEvent.OnClearQuery)
            },
            onBackClick = {
                viewModel.processEvent(MediaSearchEvent.OnBackClick)
            },
            onRecentSearchClick = { query ->
                viewModel.processEvent(MediaSearchEvent.OnRecentSearchClick(query))
            },
            onTrendingSearchClick = { query ->
                viewModel.processEvent(MediaSearchEvent.OnTrendingSearchClick(query))
            },
            onToggleMediaType = { type ->
                viewModel.processEvent(MediaSearchEvent.ToggleMediaType(type))
            },
            onApplyFilters = { filters ->
                viewModel.processEvent(MediaSearchEvent.ApplyFilters(filters))
            },
            onMediaItemClick = { item ->
                viewModel.processEvent(MediaSearchEvent.OnMediaItemClick(item))
            },
            onLoadMore = {
                viewModel.processEvent(MediaSearchEvent.LoadNextPage)
            },
            modifier = modifier
        )
    }
}
