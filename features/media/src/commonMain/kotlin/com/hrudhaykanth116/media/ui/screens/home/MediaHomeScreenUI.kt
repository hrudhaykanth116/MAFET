package com.hrudhaykanth116.media.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.ui.components.FilterBottomSheet
import com.hrudhaykanth116.media.ui.components.MediaStaggeredGrid
import com.hrudhaykanth116.media.ui.components.MediaTypeToggle
import mafet.core_ui.generated.resources.Res as CoreUIRes
import mafet.core_ui.generated.resources.ic_filter
import mafet.core_ui.generated.resources.ic_search

@Composable
fun MediaHomeScreenUI(
    uiState: MediaHomeUIState,
    onToggleMediaType: (com.hrudhaykanth116.media.domain.models.MediaType) -> Unit,
    onFilterOrientation: (com.hrudhaykanth116.media.domain.models.OrientationType) -> Unit,
    onApplyFilters: (com.hrudhaykanth116.media.domain.models.FilterState) -> Unit,
    onMediaItemClick: (MediaItem) -> Unit,
    onLoadMore: () -> Unit,
    onOpenFilters: () -> Unit,
    onOpenSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showFilterSheet by remember { mutableStateOf(false) }
    val iconColor = Color.White

    Box(
        modifier = modifier
            .fillMaxSize()
            .screenBackground()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppToolbar(
                text = "Media",
                navigationIcon = {},
                actions = {
                    AppClickableIcon(
                        resource = CoreUIRes.drawable.ic_search,
                        onClick = onOpenSearch,
                        iconColor = iconColor
                    )

                    if (uiState.mediaType == com.hrudhaykanth116.media.domain.models.MediaType.PHOTOS) {
                        AppClickableIcon(
                            resource = CoreUIRes.drawable.ic_filter,
                            onClick = {
                                showFilterSheet = true
                                onOpenFilters()
                            },
                            iconColor = iconColor
                        )
                    }
                }
            )

            MediaTypeToggle(
                selectedType = uiState.mediaType,
                onTypeSelected = onToggleMediaType
            )

            MediaStaggeredGrid(
                items = uiState.items,
                onItemClick = onMediaItemClick,
                onLoadMore = onLoadMore,
                isLoading = uiState.isLoadingMore,
                modifier = Modifier.weight(1f)
            )
        }

        if (showFilterSheet) {
            FilterBottomSheet(
                currentFilters = uiState.filters,
                mediaType = uiState.mediaType,
                onApply = { filters ->
                    onApplyFilters(filters)
                    showFilterSheet = false
                },
                onDismiss = {
                    showFilterSheet = false
                }
            )
        }
    }
}
