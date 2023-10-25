package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.core.R as CoreR
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import com.hrudhaykanth116.tv.ui.screens.updatemytv.UpdateTvScreen

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
                imageHolder = CoreR.drawable.ic_add_filled.toImageHolder(),
                iconModifier = Modifier
                    .size(40.dp)
                    .clickable {
                        tvHomeScreenCallbacks.onAddNewClicked()
                    },
                tint = Color.Green
            )
        },
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(Dimens.DEFAULT_PADDING),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppText(uiText = "My Tv list".toUIText())
                AppIcon(imageHolder = CoreR.drawable.ic_sort_vertical.toImageHolder())
            }
        }
    ) {
        it
        VerticalSpacer(height = Dimens.DEFAULT_PADDING.times(2))
        if (list.isNullOrEmpty()) {
            // TODO: Better UI
            CenteredColumn(modifier = Modifier.fillMaxSize()) {
                AppText(uiText = "No data".toUIText())
            }
        } else {
            Scaffold(

            ) { paddingValues ->
                // TODO: Use padding values
                paddingValues
                Box(modifier = Modifier.fillMaxSize()) {
                    Content(listState, it, list, tvHomeScreenCallbacks)
                    // ModalBottomSheet(
                    //     onDismissRequest = updateMyTvScreenCallbacks.onCancelled,
                    //     sheetState = bottomSheetState,
                    // )


                    state.updateTv?.let { myTvUIState ->
                        val data =
                            UpdateMyTvUIStateActual.UpdateTvData(
                                id = myTvUIState.id,
                                name = myTvUIState.name.getText(),
                                lastWatchedSeason = TextFieldValue(
                                    text = myTvUIState.lastWatchedSeason?.toString() ?: ""
                                ),
                                lastWatchedEpisode = TextFieldValue(
                                    text = myTvUIState.lastWatchedEpisode?.toString() ?: ""
                                ),
                                imgSource = myTvUIState.imgSource,
                                lastWatchedTime = myTvUIState.lastWatchedTime,
                                lastWatchedTimeUIText = TextFieldValue(text = myTvUIState.lastWatchedTimeUIText.getText()),
                            )

                        UpdateTvScreen(
                            data,
                            onCancelled = {
                                tvHomeScreenCallbacks.onUpdateTvCloseRequest()
                            },
                            // TODO: Quick way. Check this.
                            updateMyTvViewModel = hiltViewModel<UpdateMyTvViewModel>().apply {
                                setData(data)
                            },
                        )
                    }
                }
            }
        }
    }


}

@Composable
private fun Content(
    listState: LazyListState,
    it: PaddingValues,
    list: List<MyTvUIState>,
    tvHomeScreenCallbacks: TvHomeScreenCallbacks,
) {
    LazyColumn(
        state = listState,
        // Adds space between items
        verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
        // Adds padding to the row. Out side of the list item.
        contentPadding = it,
    ) {

        items(list) { myTv: MyTvUIState ->

            MyTvListItemUI(
                state = myTv,
                modifier = Modifier.clickable {
                    tvHomeScreenCallbacks.onTvListItemClicked(myTv)
                }
            )
        }
    }
}

