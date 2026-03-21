package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.constants.Dimens
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.AppToolBarIcon
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_gps
import mafet.core_ui.generated.resources.ic_refresh
import mafet.core_ui.generated.resources.ic_search

@Composable
fun WeatherHomeTopBar(
    searchText: String,
    location: String?,
    isSearchActive: Boolean,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks,
    modifier: Modifier = Modifier,
) {

    AnimatedContent(
        targetState = isSearchActive,
        transitionSpec = {
            (fadeIn() + slideInHorizontally { it / 2 }) togetherWith
                    (fadeOut() + slideOutHorizontally { -it / 2 })
        },
        label = "search_bar_transition"
    ) { showSearch ->
        if (showSearch) {
            AppSearchBar(
                text = searchText,
                placeHolderText = "Enter location name...",
                onTextChange = weatherHomeScreenCallbacks.onLocationTextChanged,
                onCancelled = weatherHomeScreenCallbacks.onSearchCancelled,
                onSearch = weatherHomeScreenCallbacks.search,
                modifier = modifier
            )
        } else {
            AppToolbar(
                text = location ?: "Unknown Location",
                navigationIcon = {},
                modifier = modifier,
                actions = {
                    AppToolBarIcon(
                        iconResId = Res.drawable.ic_refresh,
                        onClick = weatherHomeScreenCallbacks.onRefreshIconClicked,
                    )

                    AppToolBarIcon(
                        iconResId = Res.drawable.ic_gps,
                        onClick = weatherHomeScreenCallbacks.onGpsIconClicked,
                    )

                    AppToolBarIcon(
                        iconResId = Res.drawable.ic_search,
                        onClick = weatherHomeScreenCallbacks.onSearchIconClicked,
                    )
                }
            )
        }
    }
}

@AppPreview
@Composable
private fun WeatherHomeTopBarPreview() {
    AppPreviewContainer {
        CenteredColumn {
            WeatherHomeTopBar(
                searchText = "Bengaluru",
                location = "Bengaluru",
                isSearchActive = false,
                weatherHomeScreenCallbacks = WeatherHomeScreenCallbacks(),
                modifier = Modifier.padding(Dimens.DEFAULT_PADDING)
            )
        }
    }
}