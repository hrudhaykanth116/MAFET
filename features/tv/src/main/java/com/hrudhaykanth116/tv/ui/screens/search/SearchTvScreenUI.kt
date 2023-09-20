package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenState


@Composable
internal fun SearchTvScreenUI(
    state: SearchScreenState,
    searchScreenCallbacks: SearchScreenCallbacks,
    modifier: Modifier = Modifier,
) {

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            AppSearchBar(
                text = state.query,
                onTextChange = {
                    searchScreenCallbacks.onSearchTextChanged(it)
                },
                onSearch = {
                    searchScreenCallbacks.onSearchIconClicked()
                },
            )
            if (state.searchResults != null) {
                TvSearchResultsUI(list = state.searchResults)
            } else {
                AppText(uiText = "No Data".toUIText())
            }

        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}