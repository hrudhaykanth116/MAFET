package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun TvShowsScreen(
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToDetailsScreen: (Int) -> Unit,
    onBackClicked: () -> Unit,
) {
    val viewModel: TvShowsViewModel = koinViewModel()

    val lazyPagingItems: LazyPagingItems<TvShowData> = viewModel.tvShows.collectAsLazyPagingItems()


    TvShowsScreenUI(
        lazyPagingItems,
        categoryName = viewModel.category.displayName,
        onNavigateToSearchScreen = onNavigateToSearchScreen,
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        onBackClicked = onBackClicked,
        onRetry = {
            lazyPagingItems.retry()
        },
        modifier = Modifier.fillMaxSize()
    )

}