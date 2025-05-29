package com.hrudhaykanth116.chatgpt.ui.screens.query

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI
import com.hrudhaykanth116.chatgpt.ui.screens.models.QueryScreenEvent
import com.hrudhaykanth116.chatgpt.ui.screens.models.QueryScreenUIState
import kotlin.reflect.KFunction1

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
