package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.tv.domain.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.ui.components.MoviePoster

@Composable
fun TvShowsScreenUI(
    uiState: TvShowsScreenUIState,
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

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = uiState.error, color = Color.Red)
                        Text(
                            text = "Tap to retry",
                            modifier = Modifier.clickable { onRetry() }
                        )
                    }
                }
            }
            else -> {
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
                        items(uiState.tvShows) { item ->
                            MoviePoster(
                                BaseUrlConstants.IMAGES_BASE_URL + item.posterPath,
                                modifier = Modifier.clickable {
                                    onNavigateToDetailsScreen(item.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
