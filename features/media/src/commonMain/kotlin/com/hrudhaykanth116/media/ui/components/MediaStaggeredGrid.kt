package com.hrudhaykanth116.media.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.media.domain.models.MediaItem

@Composable
fun MediaStaggeredGrid(
    items: List<MediaItem>,
    onItemClick: (MediaItem) -> Unit,
    onLoadMore: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    columns: Int? = null
) {
    val spacing = 8.dp
    val targetItemWidth = 180.dp

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val effectiveColumns = columns ?: (
            ((maxWidth + spacing) / (targetItemWidth + spacing)).toInt()
        ).coerceIn(2, 6)

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(effectiveColumns),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(spacing),
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalItemSpacing = spacing
        ) {
            items(items, key = { it.id }) { item ->
                MediaCard(
                    item = item,
                    onClick = { onItemClick(item) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
