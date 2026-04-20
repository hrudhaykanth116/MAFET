package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import com.hrudhaykanth116.core.ui.components.paging.ErrorState
import com.hrudhaykanth116.core.ui.components.paging.LoadingState
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.modifier.aspectRatio
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.domain.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.ui.components.MoviePoster

@Composable
fun TvShowsScreenUI(
    lazyPagingItems: LazyPagingItems<TvShowData>,
    categoryName: String,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToDetailsScreen: (Int) -> Unit,
    onBackClicked: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier.fillMaxSize().screenBackground()
    ) {

        AppToolbar(
            text = categoryName,
            onBackClicked = onBackClicked
        )

        val spacing = 8.dp
        val targetItemWidth = 160.dp

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val effectiveColumns = (
                ((maxWidth + spacing) / (targetItemWidth + spacing)).toInt()
            ).coerceIn(2, 6)

            LazyVerticalGrid(
                columns = GridCells.Fixed(effectiveColumns),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(spacing),
                verticalArrangement = Arrangement.spacedBy(spacing),
                horizontalArrangement = Arrangement.spacedBy(spacing)
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
