package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.delay

@Composable
internal fun SearchTvScreenUI(
    state: SearchScreenState,
    searchScreenCallbacks: SearchScreenCallbacks,
    modifier: Modifier = Modifier,
) {

    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
        keyboard?.show()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            AppSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                text = state.query,
                onTextChange = {
                    searchScreenCallbacks.onSearchTextChanged(it)
                },
                onSearch = {
                    searchScreenCallbacks.onSearchIconClicked()
                },
            )
            VerticalSpacer()
            if (state.searchResults != null) {
                TvSearchResultsUI(list = state.searchResults, onAdd = searchScreenCallbacks.onAddClicked)
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