package com.hrudhaykanth116.tv.ui.screens.all

import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvHomeViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor
) : UIStateViewModel<TvHomeScreenUIState, TvHomeScreenEvent, TvHomeScreenEffect>(
    initialState = UIState.Loading(),
    defaultState = TvHomeScreenUIState(),
    networkMonitor = networkMonitor

) {

    override fun processEvent(event: TvHomeScreenEvent) {
        when (event) {

            else -> {}
        }
    }

    override fun onRetry() {

    }

    companion object {
        private const val TAG = "TvHomeViewModel"
    }

}