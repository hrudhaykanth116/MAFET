package com.hrudhaykanth116.tv.ui.screens.updatemytv

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import com.hrudhaykanth116.tv.ui.screens.home.UpdateMyTvViewModel

/**
 * This may become a quick popup dialog or entirely another screen.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UpdateTvScreen(
    updateMyTvScreenCallbacks: UpdateMyTvScreenCallbacks,
    viewModel: UpdateMyTvViewModel = hiltViewModel(),
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    UpdateMyTvScreenUI(updateMyTvScreenCallbacks, state)
}

