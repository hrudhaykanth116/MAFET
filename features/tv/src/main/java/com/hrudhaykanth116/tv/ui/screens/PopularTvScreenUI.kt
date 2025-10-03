package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.hrudhaykanth116.core.common.ui.components.paging.ErrorState
import com.hrudhaykanth116.core.common.ui.components.paging.LoadingState
import com.hrudhaykanth116.core.common.utils.compose.modifier.aspectRatio
import com.hrudhaykanth116.core.common.utils.compose.modifier.screenBackground
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.domaintemp.models.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.ui.components.MoviePoster

@Composable
fun PopularTvScreenUI(
    lazyPagingItems: LazyPagingItems<TvShowData>,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToDetailsScreen: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier.fillMaxSize().screenBackground()
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(lazyPagingItems.itemCount) { index ->
                val item = lazyPagingItems[index]
                if (item != null) {
                    MoviePoster(
                        BaseUrlConstants.IMAGES_BASE_URL + item.posterPath,
                        modifier = Modifier.clickable{
                            onNavigateToDetailsScreen(item.id)
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .aspectRatio(2f / 3f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }

            lazyPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            LoadingState("Loading TV shows…")
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            LoadingState("Loading more…")
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val e = loadState.refresh as LoadState.Error
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            ErrorState(message = e.error.localizedMessage ?: "Unknown error") {
                                retry()
                            }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val e = loadState.append as LoadState.Error
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            ErrorState(message = e.error.localizedMessage ?: "Unknown error") {
                                retry()
                            }
                        }
                    }
                }
            }
        }
    }

}


// @AppPreview
// @Composable
// private fun PopularTvScreenPreview() {
//
//     AppPreviewContainer {
//         PopularTvScreenUI(
//             processEvent = {},
//             modifier = Modifier,
//         )
//     }
//
// }
