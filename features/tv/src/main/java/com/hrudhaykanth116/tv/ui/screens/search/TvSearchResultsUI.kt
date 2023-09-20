package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState

@Composable
fun TvSearchResultsUI(
    list: List<SearchScreenItemUIState>,
) {

    val listState: LazyListState = rememberLazyListState()

    LazyColumn(
        state = listState,
        // Adds space between items
        verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
        // Adds padding to the row. Out side of the list item.
        contentPadding = PaddingValues(horizontal = Dimens.DEFAULT_PADDING),
    ) {

        items(list) { myTv ->

            TvSearchItemUI(state = myTv)

        }


    }

}