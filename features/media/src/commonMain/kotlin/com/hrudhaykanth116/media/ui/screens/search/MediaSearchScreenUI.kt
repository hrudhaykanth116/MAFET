package com.hrudhaykanth116.media.ui.screens.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.constants.Dimens
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.ui.components.FilterBottomSheet
import com.hrudhaykanth116.media.ui.components.MediaStaggeredGrid
import com.hrudhaykanth116.media.ui.components.MediaTypeToggle
import kotlinx.coroutines.delay

@Composable
fun MediaSearchScreenUI(
    uiState: MediaSearchUIState,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClearQuery: () -> Unit,
    onBackClick: () -> Unit,
    onRecentSearchClick: (String) -> Unit,
    onTrendingSearchClick: (String) -> Unit,
    onToggleMediaType: (com.hrudhaykanth116.media.domain.models.MediaType) -> Unit,
    onApplyFilters: (com.hrudhaykanth116.media.domain.models.FilterState) -> Unit,
    onMediaItemClick: (MediaItem) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showFilterSheet by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(300)
        try {
            focusRequester.requestFocus()
            keyboard?.show()
        } catch (e: IllegalStateException) {
            // Ignore focus request errors
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .screenBackground()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.DEFAULT_PADDING),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppSearchBar(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    text = uiState.query,
                    placeHolderText = "Search for photos, videos...",
                    onTextChange = onQueryChange,
                    onSearch = { onSearch(uiState.query) },
                    onCancelled = onBackClick
                )
            }

            VerticalSpacer()

            if (!uiState.showSuggestions) {
                MediaTypeToggle(
                    selectedType = uiState.mediaType,
                    onTypeSelected = onToggleMediaType
                )
            }

            if (uiState.showSuggestions) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    if (uiState.recentSearches.isNotEmpty()) {
                        Text(
                            text = "Recent Searches",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 14.ssp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.recentSearches.forEach { search ->
                                FilterChip(
                                    selected = false,
                                    onClick = { onRecentSearchClick(search) },
                                    label = {
                                        Text(search, fontSize = 13.ssp)
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Text(
                        text = "Trending",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.ssp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.trendingSearches.forEach { search ->
                            SuggestionChip(
                                onClick = { onTrendingSearchClick(search) },
                                label = {
                                    Text(search, fontSize = 13.ssp)
                                }
                            )
                        }
                    }
                }
            } else {
                MediaStaggeredGrid(
                    items = uiState.items,
                    onItemClick = onMediaItemClick,
                    onLoadMore = onLoadMore,
                    isLoading = uiState.isLoadingMore,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (!uiState.showSuggestions && uiState.items.isNotEmpty()) {
            SmallFloatingActionButton(
                onClick = { showFilterSheet = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filters"
                )
            }
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
