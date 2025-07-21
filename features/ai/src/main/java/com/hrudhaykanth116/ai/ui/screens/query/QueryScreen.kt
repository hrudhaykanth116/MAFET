package com.hrudhaykanth116.ai.ui.screens.query

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.ai.ui.screens.models.QueryScreenEvent
import com.hrudhaykanth116.ai.ui.screens.models.QueryScreenUIState

@Composable
fun QueryScreen(
    viewmodel: QueryScreenViewModel = hiltViewModel(),
) {

    val state = viewmodel.collectAsState()

    QueryScreenUI(
        state = state.value,
        onEvent = viewmodel::processEvent
    )


}

@Composable
fun QueryScreenUI(state: QueryScreenUIState, onEvent: (QueryScreenEvent) -> Unit) {


}
