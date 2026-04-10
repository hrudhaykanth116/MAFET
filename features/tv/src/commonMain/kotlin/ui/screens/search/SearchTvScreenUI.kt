package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.hrudhaykanth116.core.ui.constants.Dimens
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import kotlinx.coroutines.delay
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_search
import mafet.core_ui.generated.resources.ic_tv

@Composable
internal fun SearchTvScreenUI(
    state: SearchScreenState,
    searchScreenCallbacks: SearchScreenCallbacks,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
) {

    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(300)
        try {
            focusRequester.requestFocus()
            keyboard?.show()
        } catch (e: IllegalStateException) {
            Logger.e("SearchTvScreenUI", "Failed to request focus: ${e.message}")
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
                    text = state.query,
                    placeHolderText = "Search TV shows...",
                    onTextChange = {
                        searchScreenCallbacks.onSearchTextChanged(it)
                    },
                    onSearch = {
                        searchScreenCallbacks.onSearchIconClicked()
                    },
                    onCancelled = {
                        onBackClicked()
                    },
                )
            }

            // Result count
            if (state.searchResults.isNotEmpty()) {
                AppText(
                    uiText = "${state.searchResults.size} results".toUIText(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    modifier = Modifier.padding(
                        horizontal = Dimens.DEFAULT_PADDING * 2,
                        vertical = Dimens.DEFAULT_PADDING,
                    ),
                )
            } else {
                VerticalSpacer()
            }

            if (state.searchResults.isNotEmpty()) {
                TvSearchResultsUI(
                    list = state.searchResults,
                    onAdd = searchScreenCallbacks.onAddClicked,
                    onSearchItemClicked = searchScreenCallbacks.onSearchItemClicked,
                )
            } else if (!state.isLoading) {
                EmptySearchState(hasQuery = state.query.isNotBlank())
            }
        }

        // Loading indicator
        AnimatedVisibility(
            visible = state.isLoading,
            modifier = Modifier.align(Alignment.Center),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 3.dp,
            )
        }
    }
}

@Composable
private fun EmptySearchState(hasQuery: Boolean) {
    CenteredColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        AppIcon(
            resource = if (hasQuery) Res.drawable.ic_tv else Res.drawable.ic_search,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        )
        VerticalSpacer(height = 16.dp)
        AppText(
            uiText = (if (hasQuery) "No shows found" else "Search for TV shows").toUIText(),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        )
        VerticalSpacer(height = 4.dp)
        AppText(
            uiText = (if (hasQuery) "Try a different search term" else "Type a name to get started").toUIText(),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
            ),
        )
    }
}

@AppPreview
@Composable
private fun SearchTvScreenUIPreview() {
    AppPreviewContainer {
        CenteredColumn {
            SearchTvScreenUI(
                state = SearchScreenState(
                    query = "Avengers",
                    isLoading = false,
                ),
                searchScreenCallbacks = SearchScreenCallbacks(
                    onSearchItemClicked = {},
                    onAddClicked = { },
                    onSearchIconClicked = {},
                    onSearchTextChanged = {}
                )
            )
        }
    }
}
