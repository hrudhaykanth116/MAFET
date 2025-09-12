package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData

@Composable
fun PopularTvScreen(
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToDetailsScreen: (Int) -> Unit,
    viewModel: PopularTvViewModel = hiltViewModel(),
) {

    val lazyPagingItems: LazyPagingItems<TvShowData> = viewModel.popularTvShows.collectAsLazyPagingItems()


    PopularTvScreenUI(
        lazyPagingItems,
        onNavigateToSearchScreen = onNavigateToSearchScreen,
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        onRetry = {
            lazyPagingItems.retry()
        },
        modifier = Modifier.fillMaxSize()
    )

}