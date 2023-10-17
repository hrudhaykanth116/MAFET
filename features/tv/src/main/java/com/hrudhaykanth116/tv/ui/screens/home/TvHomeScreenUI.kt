package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEvent
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
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
                imageHolder = com.hrudhaykanth116.core.R.drawable.ic_add_filled.toImageHolder(),
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        tvHomeScreenCallbacks.onAddNewClicked()
                    },
                tint = Color.Green
            )
        },
        topBar = {
            AppText(uiText = "My Tv list".toUIText())
        }
    ) {
        it
        VerticalSpacer()
        if (list.isNullOrEmpty()) {
            // TODO: Better UI
            CenteredColumn {
                AppText(uiText = "No data".toUIText())
            }
        } else {

            // Scaffold(
            //     modifier = Modifier
            //         .fillMaxSize()
            //         .background(color = Color.Red),
            // )
            // {
            //     Content(listState, it, list, tvHomeScreenCallbacks)
            // }
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
                                name = myTvUIState.name,
                                lastWatchedSeason = TextFieldValue(text = myTvUIState.lastWatchedSeason.orEmpty()),
                                lastWatchedEpisode = TextFieldValue(text = myTvUIState.lastWatchedEpisode.orEmpty()),
                                lastWatchedSeasonEpisode = myTvUIState.lastWatchedSeasonEpisode,
                                lastWatchedTime = myTvUIState.lastWatchedTime,
                                imgSource = myTvUIState.imgSource,
                            )

                        val factory: ViewModelProvider.Factory =
                            object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return UpdateMyTvViewModel(data) as T
                                }
                            }

                        val updateMyTvViewModel = viewModel<UpdateMyTvViewModel>(
                            factory = factory,
                        )

                        UpdateTvScreen(
                            viewModel = updateMyTvViewModel,
                            updateMyTvScreenCallbacks = UpdateMyTvScreenCallbacks(
                                onSeasonChanged = {
                                    updateMyTvViewModel.processEvent(
                                        UpdateMyTvScreenEvent.OnSeasonChanged(
                                            it
                                        )
                                    )
                                },
                                onEpisodeChanged = {
                                    updateMyTvViewModel.processEvent(
                                        UpdateMyTvScreenEvent.OnEpisodeChanged(
                                            it
                                        )
                                    )
                                },
                                onCancelled = {
                                    tvHomeScreenCallbacks.onUpdateTvCloseRequest()
                                }
                            )

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

