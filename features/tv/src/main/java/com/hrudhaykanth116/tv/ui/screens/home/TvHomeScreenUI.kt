package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.foundation.background
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
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissDefaults
import androidx.compose.material3.rememberDismissState
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
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import com.hrudhaykanth116.tv.ui.screens.updatemytv.UpdateTvScreen
import com.hrudhaykanth116.core.R as CoreR

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.DEFAULT_PADDING),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppText(uiText = "My Tv list".toUIText())
                AppIcon(imageHolder = CoreR.drawable.ic_sort_vertical.toImageHolder())
            }
        }
    ) {
        if (list.isNullOrEmpty()) {
            // TODO: Better UI
            CenteredColumn(modifier = Modifier.fillMaxSize()) {
                AppText(uiText = "No data".toUIText())
            }
        } else {
            Box(modifier = Modifier.padding(it)) {
                Content(listState, list, tvHomeScreenCallbacks)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    listState: LazyListState,
    list: List<MyTvUIState>,
    tvHomeScreenCallbacks: TvHomeScreenCallbacks,
) {
    LazyColumn(
        state = listState,
        // Adds space between items
        verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
        // Adds padding to the row. Out side of the list item.
        contentPadding = PaddingValues(vertical = Dimens.DEFAULT_PADDING),
    ) {

        items(list, key = { item -> item.id }) { myTv: MyTvUIState ->

            val dismissState = rememberDismissState(
                positionalThreshold = {
                    // After x dp movement, dismisses or else retracts
                    170.dp.toPx()
                },
                confirmValueChange = {
                    if(it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd){
                        tvHomeScreenCallbacks.onTvListItemDismissed(myTv.id)
                    }
                    true
                }
            )

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                // TODO: P10 Use ?
                // This is called for below items as well. check
            }

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(
                    DismissDirection.EndToStart
                ),
                background = {
                    // dismissState.dismissDirection ?:  return@SwipeToDismiss
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimens.DEFAULT_PADDING),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // AppIcon(
                        //     imageHolder = CoreR.drawable.ic_delete.toImageHolder(),
                        //     modifier = Modifier
                        // )
                        AppText(uiText = "Swipe to delete".toUIText())
                    }
                },
                dismissContent = {
                    MyTvListItemUI(
                        state = myTv,
                        modifier = Modifier.clickable {
                            tvHomeScreenCallbacks.onTvListItemClicked(myTv)
                        }
                    )
                },
            )
        }
    }
}

