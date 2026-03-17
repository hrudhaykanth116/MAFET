package com.hrudhaykanth116.ai.ui.screens.query

import androidx.compose.runtime.Composable
import com.hrudhaykanth116.ai.ui.screens.models.QueryScreenEvent
import com.hrudhaykanth116.ai.ui.screens.models.QueryScreenUIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun QueryScreen(
    viewmodel: QueryScreenViewModel = koinViewModel(),
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
