package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIState
import com.hrudhaykanth116.tv.ui.screens.updatemytv.UpdateTvScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvHomeScreenUI(
    state: TvHomeScreenUIState,
    tvHomeScreenCallbacks: TvHomeScreenCallbacks,
) {

    val list = state.tvShows

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
        if (list.isNullOrEmpty()) {
            // TODO: Better UI
            CenteredColumn {
                AppText(uiText = "No data".toUIText())
            }
        } else {
            Box {
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

                state.updateTv?.let{
                    UpdateTvScreen(
                        state = UpdateMyTvUIState(
                            id = it.id,
                            name = it.name,
                            lastWatchedSeason = it.lastWatchedSeason,
                            lastWatchedEpisode = it.lastWatchedEpisode,
                            lastWatchedSeasonEpisode = it.lastWatchedSeasonEpisode,
                            lastWatchedTime = it.lastWatchedTime,
                            imgSource = it.imgSource,

                        ),
                        updateMyTvScreenCallbacks = UpdateMyTvScreenCallbacks(
                            onSeasonChanged = {},
                            onCancelled = {}
                        )

                    )
                }


            }
        }
    }


}

