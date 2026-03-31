package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.AppToolBarIcon
import com.hrudhaykanth116.weather.domain.models.LocationSource
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_gps
import mafet.core_ui.generated.resources.ic_refresh
import mafet.core_ui.generated.resources.ic_search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeTopBar(
    searchText: String,
    location: String?,
    locationSource: LocationSource,
    lastFetchedTimestamp: Long?,
    formattedTimestamp: String?,
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
            TopAppBar(
                modifier = modifier,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                title = {
                    Column {
                        val locationLabel = when (locationSource) {
                            LocationSource.CURRENT -> "Current Location"
                            LocationSource.LAST -> "Last Location"
                            LocationSource.UNAVAILABLE -> "Location unavailable"
                        }

                        Text(
                            text = locationLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = location ?: "Unknown",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (formattedTimestamp != null && lastFetchedTimestamp != null) {
                            Text(
                                text = "Last fetched: $formattedTimestamp",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.6f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                navigationIcon = {},
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
                },
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    }
}
