package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.room.util.query
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.modifier.screenBackground
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppRoundedIcon
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay

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
        focusRequester.requestFocus()
        keyboard?.show()
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
                AppClickableIcon(
                    resId = com.hrudhaykanth116.core.R.drawable.ic_back,
                    iconColor = Color.White,
                    modifier = Modifier,
                    onClick = {
                        onBackClicked()
                    }
                )
                HorizontalSpacer()
                AppSearchBar(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    text = state.query,
                    onTextChange = {
                        searchScreenCallbacks.onSearchTextChanged(it)
                    },
                    onSearch = {
                        searchScreenCallbacks.onSearchIconClicked()
                    },
                    onCancelled = {
                        Logger.d("hrudhay_logs", ": SearchTvScreenUI: onCancelled")
                    }
                )
            }
            VerticalSpacer()
            if (state.searchResults.isNotEmpty()) {
                TvSearchResultsUI(
                    list = state.searchResults,
                    onAdd = searchScreenCallbacks.onAddClicked,
                    onSearchItemClicked = searchScreenCallbacks.onSearchItemClicked
                )
            } else {
                CenteredColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppText(uiText = "No Data".toUIText(), fontSize = 20.ssp)
                }
            }

        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
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