package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenState

@Composable
fun SearchTvScreen(
    searchTvScreenViewModel: SearchTvScreenViewModel = hiltViewModel(),
) {

    val state: State<SearchScreenState> =
        searchTvScreenViewModel.stateFlow.collectAsStateWithLifecycle()

    SearchTvScreenUI(
        state.value
    )

}