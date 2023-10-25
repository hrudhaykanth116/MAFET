package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenEvent
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenState

@Composable
fun SearchTvScreen(
    viewModel: SearchTvScreenViewModel = hiltViewModel(),
) {

    val state: State<SearchScreenState> =
        viewModel.stateFlow.collectAsStateWithLifecycle()

    SearchTvScreenUI(
        state.value,
        searchScreenCallbacks = SearchScreenCallbacks(
            onSearchTextChanged = {
                viewModel.processEvent(SearchScreenEvent.OnSearchTextChanged(it))
            },
            onSearchIconClicked = {
                viewModel.processEvent(SearchScreenEvent.OnSearchIconClicked)
            },
            onAddClicked = {
                viewModel.processEvent(SearchScreenEvent.OnAddClicked(it))
            }
        ),
    )

}