package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.hrudhaykanth116.core.common.ui.components.paging.ErrorState
import com.hrudhaykanth116.core.common.ui.components.paging.LoadingState
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.modifier.aspectRatio
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.domaintemp.models.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.ui.components.MoviePoster

@Composable
fun PopularTvScreenUI(
    lazyPagingItems: LazyPagingItems<TvShowData>,
    onNavigateToSearchScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {

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
                    MoviePoster((BaseUrlConstants.IMAGES_BASE_URL + item.posterPath))
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
