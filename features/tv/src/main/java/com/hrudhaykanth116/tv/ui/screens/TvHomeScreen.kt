package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState

@Composable
fun TvHomeScreen(
    tvHomeScreenViewModel: TvHomeScreenViewModel = hiltViewModel(),
) {

    val state: State<TvHomeScreenUIState> =
        tvHomeScreenViewModel.stateFlow.collectAsStateWithLifecycle()





}