package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.common.utils.ui.ToastHelper
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.tv.ui.common.TvShowListItems
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState

@Composable
fun TvHomeScreen(
    tvHomeScreenViewModel: TvHomeScreenViewModel = hiltViewModel(),
) {

    val state: State<TvHomeScreenUIState> =
        tvHomeScreenViewModel.stateFlow.collectAsStateWithLifecycle()

    val userMessage = state.value.userMessage
    if(userMessage != null){
        ToastHelper.show(LocalContext.current, userMessage)
    }


    state.value.tvShows?.let {
        TvShowListItems(nullableTvShows = it)
    } ?: run {
        CenteredColumn {
            AppText(uiText = "Loading".toUIText())
        }
    }



}