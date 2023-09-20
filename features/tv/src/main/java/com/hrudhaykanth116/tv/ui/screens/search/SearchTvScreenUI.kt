package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenState


@Composable
internal fun SearchTvScreenUI(state: SearchScreenState) {

    Column {
        AppSearchBar(text = state.query)
    }

}