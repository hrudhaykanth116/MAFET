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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.utils.compose.modifier.screenBackground
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenUIState
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import com.hrudhaykanth116.tv.ui.screens.updatemytv.UpdateTvScreen
import com.hrudhaykanth116.core.R as CoreR

@Composable
fun EntertainmentHomeScreenUI(
    state: EntertainmentHomeScreenUIState,
    entertainmentHomeScreenCallbacks: EntertainmentHomeScreenCallbacks,
) {

    val list = state.tvShows

    val listState: LazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.screenBackground(),
        floatingActionButton = {
            AppIcon(
                resId = CoreR.drawable.ic_add_filled,
                iconModifier = Modifier
                    .size(40.dp)
                    .clickable {
                        entertainmentHomeScreenCallbacks.onAddNewClicked()
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
                // AppIcon(imageHolder = CoreR.drawable.ic_sort_vertical.toImageHolder())
            }
        }
    ) {
        if (list.isNullOrEmpty()) {
            // hrudhay_check_list: Better UI
            CenteredColumn(modifier = Modifier.fillMaxSize()) {
                AppText(uiText = "No data".toUIText())
            }
        } else {
            Box(modifier = Modifier.padding(it)) {

                LazyColumn(
                    state = listState,
                    // Adds space between items
                    verticalArrangement = Arrangement.spacedBy(Dimens.DEFAULT_PADDING),
                    // Adds padding to the row. Out side of the list item.
                    contentPadding = PaddingValues(vertical = Dimens.DEFAULT_PADDING),
                ) {

                    items(list, key = { item -> item.id }) { myTv: MyTvUIState ->
                        MyTvListItemUI(
                            state = myTv,
                            modifier = Modifier.clickable {
                                entertainmentHomeScreenCallbacks.onTvListItemClicked(myTv)
                            }
                        )
                    }
                }

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
                            entertainmentHomeScreenCallbacks.onUpdateTvCloseRequest()
                        },
                        // hrudhay_check_list: Quick way. Check this.
                        updateMyTvViewModel = hiltViewModel<UpdateMyTvViewModel>().apply {
                            setData(data)
                        },
                    )
                }
            }
        }
    }


}

@Preview
@Composable
private fun TvHomeScreenUIPreview() {
    EntertainmentHomeScreenUI(
        state = EntertainmentHomeScreenUIState(
            tvShows = listOf(
                MyTvUIState(
                    id = 1,
                    name = "name".toUIText(),
                    lastWatchedSeason = 1,
                    lastWatchedEpisode = 4788,
                    lastWatchedSeasonEpisode = UIText.Text("season 1"),
                    lastWatchedTimeUIText = UIText.Text("episode 14"),
                    lastWatchedTime = 9953,
                    imgSource = null,

                )
            )
        ),
        entertainmentHomeScreenCallbacks = EntertainmentHomeScreenCallbacks(
            {}, {}, {}, {}
        )
    )
}
