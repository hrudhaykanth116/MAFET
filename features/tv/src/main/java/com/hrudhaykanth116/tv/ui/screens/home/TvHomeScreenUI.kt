package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState

@Composable
fun TvHomeScreenUI(
    list: List<MyTvUIState>,
    tvHomeScreenCallbacks: TvHomeScreenCallbacks
) {

    val listState: LazyListState = rememberLazyListState()

    Scaffold(
        floatingActionButton = {
            AppIcon(
                imageHolder = com.hrudhaykanth116.core.R.drawable.ic_save.toImageHolder(),
                modifier = Modifier.clickable {
                    tvHomeScreenCallbacks.onAddNewClicked()
                }
            )
        }
    ) {
        LazyColumn(
            state = listState,
            // Adds space between items
            verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
            // Adds padding to the row. Out side of the list item.
            contentPadding = it,
        ) {

            items(list) { myTv ->

                MyTvListItemUI(state = myTv)

            }

        }
    }


}

