package com.hrudhaykanth116.tv.ui.screens.updatemytv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEvent
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import com.hrudhaykanth116.tv.ui.screens.home.UpdateMyTvViewModel

/**
 * This may become a quick popup dialog or entirely another screen.
 */
@Composable
fun UpdateTvScreen(
    data: UpdateMyTvUIStateActual.UpdateTvData,
    onCancelled: () -> Unit,
    updateMyTvViewModel: UpdateMyTvViewModel,
) {

    // TODO: For each new composable we need to create new view model with updated data
    // val factory: ViewModelProvider.Factory =
    //     object : ViewModelProvider.Factory {
    //         override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
    //             return UpdateMyTvViewModel(data) as T
    //         }
    //     }
    //
    //
    // val updateMyTvViewModel = viewModel<UpdateMyTvViewModel>(
    //     factory = factory,
    // )

    val updateMyTvScreenCallbacks = UpdateMyTvScreenCallbacks(
        onSeasonChanged = {
            updateMyTvViewModel.processEvent(
                UpdateMyTvScreenEvent.OnSeasonChanged(
                    it
                )
            )
        },
        onEpisodeChanged = {
            updateMyTvViewModel.processEvent(
                UpdateMyTvScreenEvent.OnEpisodeChanged(
                    it
                )
            )
        },
        onSubmit = {
            updateMyTvViewModel.processEvent(
                UpdateMyTvScreenEvent.OnSubmit
            )
        },

        onCancelled = onCancelled,
        onLastWatchedDateChanged = {
            updateMyTvViewModel.processEvent(
                UpdateMyTvScreenEvent.OnLastWatchedDateChanged(it)
            )
        },
        onLastWatchedDatePickerCloseRequest = {
            updateMyTvViewModel.processEvent(
                UpdateMyTvScreenEvent.OnLastWatchedDatePickerCloseRequest
            )
        },
        onLastWatchedDatePickerOpenRequest = {
            updateMyTvViewModel.processEvent(
                UpdateMyTvScreenEvent.OnLastWatchedDatePickerOpenRequest
            )
        },

    )


    val state by updateMyTvViewModel.stateFlow.collectAsStateWithLifecycle()

    if (state.isClosed) {
        // TODO: New state ?
        updateMyTvScreenCallbacks.onCancelled()
        updateMyTvViewModel.resetState()
    }else{
        UpdateMyTvScreenUI(updateMyTvScreenCallbacks, state)
    }

}

