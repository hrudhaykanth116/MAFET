package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun PopularTvScreen(
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToDetailsScreen: (Int) -> Unit,
    onBackClicked: () -> Unit,
) {
    val viewModel: PopularTvViewModel = koinViewModel()

    val lazyPagingItems: LazyPagingItems<TvShowData> = viewModel.popularTvShows.collectAsLazyPagingItems()


    PopularTvScreenUI(
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