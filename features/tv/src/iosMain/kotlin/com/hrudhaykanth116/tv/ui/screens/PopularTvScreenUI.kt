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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.modifier.aspectRatio
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.tv.domain.usecases.models.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.ui.components.MoviePoster

@Composable
fun PopularTvScreenUI(
    uiState: PopularTvScreenUIState,
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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
