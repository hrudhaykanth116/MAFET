package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.constants.Dimens
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun TvSearchResultsUI(
    list: List<SearchScreenItemUIState>,
    onAdd: (Int) -> Unit,
    onSearchItemClicked: (Int) -> Unit,
) {

    val listState: LazyListState = rememberLazyListState()

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
        contentPadding = PaddingValues(horizontal = Dimens.DEFAULT_PADDING),
    ) {

        itemsIndexed(list, key = { _, item -> item.id }) { _, myTv ->

            TvSearchItemUI(
                state = myTv,
                onAdd = {
                    onAdd(myTv.id)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(
                        fadeInSpec = tween(300),
                        fadeOutSpec = tween(300),
                    )
                    .clickable {
                        onSearchItemClicked(myTv.id)
                    },
            )

        }

    }

}
